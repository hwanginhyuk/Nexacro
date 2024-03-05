package kr.co.seoulit.insa.sys.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.nexacro17.xapi.data.*;
import com.nexacro17.xapi.tx.HttpPlatformResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.nexacro17.xapi.tx.HttpPlatformRequest;

import com.nexacro17.xapi.tx.PlatformType;

public class NexacroInterceptor implements HandlerInterceptor {

    @Override //preHandle는 요청을 처리하기전에 호출되는 메서드
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("@@@@@@@@@@@@NexacroInterceptor preHandle메서드 작동");

        // HTTP 요청으로부터 데이터 (PlatformData)를 수신 받는다.
        HttpPlatformRequest httpPlatformRequest = new HttpPlatformRequest(request);
        /*
         * 송수신 형식(contentType)이 설정되지 않은 경우 HTTP 헤더의 ContentType 값으로부터 판단하며, 다음과 같이 적용된다.
         * HTTP 헤더의 ContentType     적용되는 송수신 형식(contentType)
         * text/xml                    PlatformType.CONTENT_TYPE_XML
         * application/octet-stream    PlatformType.CONTENT_TYPE_BINARY
         * 그 외                       PlatformType.DEFAULT_CONTENT_TYPE
         */

        //// XML parsing(parsing 이란 A 형태의 자료구조를 B 형태로 바꾸는것)
        // 클라이언트 쪽에서 엄어온 xml 데이터를 항당받는 부분
        httpPlatformRequest.receiveData();


        //xml로 되어있는 데이터 셋을 java 데이터셋으로 바꿔주고 안에 간직해주는 메서드
        // 엑플에서 transaction 요청할 때, 클라이언트쪽에서 넘어온 데이터를 받기위한 PlatformData
        PlatformData reqData = httpPlatformRequest.getData();

        // 서버에서 클라이언트에게 보내기위해 객체 생성
        PlatformData resData = new PlatformData();

        // 클라이언트쪽에서 받아온 데이터 셋과 변수를 debug
        debug(reqData.getDataSetList(), reqData.getVariableList());
        System.out.println("========================" + reqData.getDataSetList() + "=====================찍히는값");

        // HttpServletRequest 객체에 set을 해준뒤 컨트롤러에서 getAttribute()꺼낼수있다.
        request.setAttribute("reqData", reqData);
        request.setAttribute("variableList", reqData.getVariableList());
        request.setAttribute("resData", resData);

        // 🌟전달 값 확인 방법
        // PlatformData의 데이터셋 목록 출력
        DataSetList dataSetList = reqData.getDataSetList();
        for (int i = 0; i < dataSetList.size(); i++) {
            DataSet dataSet = dataSetList.get(i);
            System.out.println("🚨🚨🚨DataSet " + i + ":\n" + dataSet.toString());
        }

        // VariableList의 변수 목록 출력
        VariableList variableList = reqData.getVariableList();
        for (int i = 0; i < variableList.size(); i++) {
            Variable variable = variableList.get(i);
            System.out.println("🚨🚨🚨Variable " + i + ": " + variable.getName() + " = " + variable.getObject());
        }


        System.out.println("@@@@@@@@@@@@NexacroInterceptor preHandle메서드 종료");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("@@@@@@@@@@@@NexacroInterceptor postHandle메서드 접근");
    }

    // 컨트롤러 진입 후 view가 정상적으로 랜더링 된 후 제일 마지막에 실행이 되는 메서드이다.
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception {
        System.out.println("@@@@@@@@@@@@NexacroInterceptor afterCompletion메서드 접근");

        //컨트롤러단에서 HttpServletRequest격체에 setAttribute 해줬던걸 꺼낸다.
        PlatformData resData = (PlatformData) request.getAttribute("resData");

//        if(resData==null){
//           return;
//        }

        //단일 데이터를 가지고 있는 Variable들을 저장한다. Variable은 식별자(name) 또는 위치(index)를 통하여 참조할 수 있다.
        VariableList variableList = resData.getVariableList();

        //서버에서 exception 발행  여부를 확인 후  아래 변수에 값을 넣어 view 단으로 보내 콜백함수  호출시 이용
        if (exception != null) {
            //exception.printStackTrace();
            variableList.add("ErrorCode", -1);
            variableList.add("ErrorMsg", exception.getMessage());
        } else {
            variableList.add("ErrorCode", 0);
            variableList.add("ErrorMsg", "success");
        }

        // HTTP 응답으로 데이터 (PlatformData)를 송신한다.
        // HttpServletResponse, 송수신 형식(contentType)과 문자셋(charset)을 가지는 생성자이다.
        //HttpServletResponse 객체를 이용하여 HttpPlatformResponse 생성
        //생성자 (HttpServletResponse httpRes, String contentType, String charset)
        HttpPlatformResponse httpPlatformResponse = new HttpPlatformResponse(response, PlatformType.CONTENT_TYPE_XML, "UTF-8");
        //(response는 출력 스트림을 갖고있다.)

        //데이터 셋팅
        //(PlatformData data)
        httpPlatformResponse.setData(resData);
        //데이터 송신
        httpPlatformResponse.sendData();

        debug(resData.getDataSetList(), resData.getVariableList());

        resData = null;

        System.out.println("@@@@@@@@@@@@NexacroInterceptor afterCompletion메서드 종료");
    }

    private void debug(DataSetList dataSetList, VariableList variableList) {
        System.out.println("@@@@@@@@@@@@NexacroInterceptor debug메서드 접근");
        Debugger debugger = new Debugger();
        // DEBUG - DataSet

        for (int n = 0; n < dataSetList.size(); n++) {
            System.out.println("debug11@@@@@@" + debugger.detail(dataSetList.get(n)));
        }
        // DEBUG - VariableList
        for (int n = 0; n < variableList.size(); n++) {
            System.out.println("debug22@@@@@@" + debugger.detail(variableList.get(n)));
        }
        System.out.println("@@@@@@@@@@@@NexacroInterceptor debug메서드 종료");
    }
}
