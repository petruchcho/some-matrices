package endpoint;

import calculations.Api;
import calculations.core.Result;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

@org.springframework.stereotype.Controller
public class Controller {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Response greeting(Request request) throws Exception {
        String content = makeContent(
                Api.completeResult(request.getFormula(), request.getGamma(), request.getBetta(), request.getOrder())
        );
        return new Response(content);
    }

    private String makeContent(Result result) {
        String content = "Выполнение успешно завершено.\n" +
                "Время выполнения последовательной программы:\n" +
                "   вычисление матрицы = %s ms\n" +
                "   транспонирование = %s ms\n" +
                "   обратная = %s ms";
        return String.format(content, result.timeMatrixCons, result.timeMatrixTCons, result.timeMatrixInversCons);
    }
}
