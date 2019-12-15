package com.ljt.study.dp.filter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LiJingTang
 * @date 2019-12-15 21:32
 */
public class FilterTest {

    public static void main(String[] args) {
        Request request = new Request();
        request.setReqMsg("请求信息");
        Response response = new Response();
        response.setRespMsg("返回信息");
        FilterChain chain = new FilterChain();
        chain.addFilter(new HtmlFilter()).addFilter(new EncodingFilter());
        chain.doFilter(request, response);

        System.out.println(request.getReqMsg());
        System.out.println(response.getRespMsg());
    }

    private static class HtmlFilter implements Filter {

        @Override
        public void doFilter(Request request, Response response, FilterChain chain) {
            request.setReqMsg(request.getReqMsg() + "-->HtmlFilter");
            chain.doFilter(request, response);
            response.setRespMsg(response.getRespMsg() + "-->HtmlFilter");
        }
    }

    private static class EncodingFilter implements Filter {

        @Override
        public void doFilter(Request request, Response response, FilterChain chain) {
            request.setReqMsg(request.getReqMsg() + "-->EncodingFilter");
            chain.doFilter(request, response);
            response.setRespMsg(response.getRespMsg() + "-->EncodingFilter");
        }
    }

    private interface Filter {

        void doFilter(Request request, Response response, FilterChain chain);

    }

    private static class FilterChain {

        private List<Filter> filterList = new ArrayList<>();
        private int index;

        public FilterChain addFilter(Filter filter) {
            if (filter != null) {
                this.filterList.add(filter);
            }

            return this;
        }

        void doFilter(Request request, Response response) {
            if (index == filterList.size()) {
                return;
            }

            this.filterList.get(index++).doFilter(request, response, this);
        }
    }

    private static class Request {

        private String reqMsg;

        public String getReqMsg() {
            return reqMsg;
        }

        public void setReqMsg(String reqMsg) {
            this.reqMsg = reqMsg;
        }
    }

    private static class Response {

        private String respMsg;

        public String getRespMsg() {
            return respMsg;
        }

        public void setRespMsg(String respMsg) {
            this.respMsg = respMsg;
        }
    }

}
