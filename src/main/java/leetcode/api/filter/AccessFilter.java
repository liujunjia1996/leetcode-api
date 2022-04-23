package leetcode.api.filter;

import cn.hutool.json.JSONUtil;
import leetcode.api.pojo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Component
@Order(1)
@Slf4j
public class AccessFilter implements Filter {

    static final String JSON = "application/json";

    static final String ACCESS_TOKEN = "access-token";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        resp.setHeader("Access-Control-Max-Age", "3600");
        resp.setHeader("Access-Control-Allow-Headers", ACCESS_TOKEN);

        var token = req.getHeader(ACCESS_TOKEN);
        if (Objects.equals(token, "day5") || Objects.equals(req.getServletPath(), "/")) {
            filterChain.doFilter(request, response);
        } else {
            returnJson(response, JSONUtil.toJsonStr(CommonResponse.fail(400, "token error")));
        }
    }

    private void returnJson(ServletResponse response, String json) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(JSON);
        try (PrintWriter writer = response.getWriter()) {
            writer.print(json);
        } catch (IOException e) {
            log.error("response error", e);
        }
    }


}
