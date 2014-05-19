import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 注释：判断三角形
 * 时间：2014年05月12日 下午4:20
 * 作者：冯亚楠[fengyanan]
 */
public class TriangleTest {

    public static void main(String[] args) {
        while (true) {
            TriangleTest test = new TriangleTest();
            try {
                int[] dataArr = test.getInputData();
                int a = dataArr[0];
                int b = dataArr[1];
                int c = dataArr[2];
                Triangle triangle = new Triangle(a, b, c);
                if (triangle.isLegal(triangle)) {
                    if (triangle.isIsoscelesTriangle(triangle)) {
                        if (triangle.isEqualSides(triangle)) {
                            System.err.println("等边三角形");
                        } else {
                            System.err.println("等腰三角形");
                        }
                    } else {
                        System.err.println("普通三角形");
                    }
                } else {
                    System.err.println("不是三角形");
                }
            } catch (IOException e) {
                return;
            }
        }
    }


    /**
     * 获取录入的数据，以“，”号分开
     */
    private int[] getInputData() throws IOException {
        int[] intArr = new int[3];
        while (true) {
            System.out.print("请输入正确的三个数字，以“,”分割:");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String str = reader.readLine();
            if (str == null || str.length() == 0) {
                break;
            }
            String[] dataArr = str.split(",");
            if (dataArr.length != 3) {
                break;
            }
            for (int index = 0; index < dataArr.length; index++) {
                try {
                    Integer intData = Integer.valueOf(dataArr[index]);
                    intArr[index] = intData;
                } catch (Exception e) {
                    break;
                }
            }
            break;
        }
        return intArr;
    }


}
