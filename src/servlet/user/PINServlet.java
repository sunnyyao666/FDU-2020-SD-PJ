package servlet.user;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@WebServlet(name = "PINServlet")
public class PINServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //禁止缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        char[] codeSequence = {'A', '1', 'B', 'C', '2', 'D', '3', 'E', '4', 'F', '5', 'G', '6', 'H', '7', 'I', '8', 'J',
                'K', '9', 'L', '1', 'M', '2', 'N', 'P', '3', 'Q', '4', 'R', 'S', 'T', 'U', 'V', 'W',
                'X', 'Y', 'Z'};
        int width = 63;
        int height = 37;
        Random random = new Random();
        //生成缓冲区image类
        BufferedImage image = new BufferedImage(width, height, 1);
        Graphics g = image.getGraphics();
        //设置样式
        g.setColor(this.getColor(200, 250));
        g.setFont(new Font("Times New Roman", Font.PLAIN, 28));
        g.fillRect(0, 0, width, height);
        //绘制干扰线
        for (int i = 0; i < 40; i++) {
            g.setColor(this.getColor(130, 200));
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int x1 = random.nextInt(12);
            int y1 = random.nextInt(12);
            g.drawLine(x, y, x + x1, y + y1);
        }

        //绘制字符
        StringBuilder strCode = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            String rand = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
            strCode.append(rand);
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand, 13 * i + 6, 28);
        }
        //将字符保存到session中用于前端的验证
        request.getSession().setAttribute("PIN", strCode.toString().toLowerCase());
        g.dispose();

        ImageIO.write(image, "JPEG", response.getOutputStream());
        response.getOutputStream().flush();
    }

    public Color getColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) fc = 255;
        if (bc > 255) bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}
