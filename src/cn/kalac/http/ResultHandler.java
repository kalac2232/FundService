package cn.kalac.http;

import cn.kalac.bean.ResultBean;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResultHandler {

    public static void onResult(HttpServletResponse response, ResultBean resultBean) {
        System.out.println(resultBean.toString());
        response.setStatus(200);

        response.setCharacterEncoding("utf-8");

        response.setContentType("text/html;chaset=utf-8");
        //获取字符输出流
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            //输出字符
            pw.write(resultBean.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void onError(HttpServletResponse response, String errorMsg) {
        response.setStatus(200);

        response.setCharacterEncoding("utf-8");

        response.setContentType("text/html;chaset=utf-8");
        //获取字符输出流
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            //输出字符
            pw.write(new ResultBean(ResultCode.ERROR,errorMsg).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
