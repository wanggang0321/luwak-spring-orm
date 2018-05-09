import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;

public class JdbcTest {

    public static void main(String[] args) {

        List<?> result = select(Member.class);

        System.out.println(Arrays.toString(result.toArray()));

    }

    private static List<?> select(Class<?> entityClass) {
        try {
            //1、加载驱动类
            Class.forName("com.mysql.jdbc.Driver");

            //2、建立连接
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/luwak");

            Table table = entityClass.getAnnotation(Table.class);

            //3、创建语句开始事务
            PreparedStatement ps = connection.prepareStatement("select * from " + table.name());

            //4、执行语句集
            ResultSet rs = ps.executeQuery();

            //5、获取结果集
            //数据表中的记录要赋值到Java的Object中
            //反射机制
            //自动赋值

            //非常关键的一点：
            //拿到一共有多少个列
            int columnCount = rs.getMetaData().getColumnCount();
            while(rs.next()) {

                Object instance = entityClass.newInstance();
                for (int i=1;i<=columnCount;i++) {
                    String columnName = rs.getMetaData().getColumnName(i);
                    Field field = entityClass.getDeclaredField(columnName);
                    field.setAccessible(true);
                }

                System.out.println(rs.getLong("id"));
                System.out.println(rs.getString("name"));
            }

            //==================Begin ORM==================
            //==================End ORM==================

            //6、关闭结果集、关闭语句集、关闭连接

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭资源要在finally块中

        }
        return null;
    }

}
