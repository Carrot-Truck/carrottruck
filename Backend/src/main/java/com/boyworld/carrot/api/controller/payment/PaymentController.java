package com.boyworld.carrot.api.controller.payment;

import com.boyworld.carrot.api.ApiResponse;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Port One 결제 API 호출 컨트롤러
 *
 * @author 최영환
 */
@Slf4j
@RestController
public class PaymentController {

    @Value("${iamport.key}")
    private String restApiKey;

    @Value("${iamport.secret}")
    private String restApiSecret;

    private final IamportClient iamportClient;

    public PaymentController() {
        this.iamportClient = new IamportClient(restApiKey, restApiSecret);
    }

    /**
     * 결제 요청 API
     *
     * @param impUid 가맹점 번호
     * @return 결제 성공 여부
     * @throws IamportResponseException IamPort Exception 발생 시
     * @throws IOException API 호출 중 Exception 발생 시
     */
    @PostMapping("/verifyIamport/{impUid}")
    public ApiResponse<IamportResponse<Payment>> paymentByImpUid(@PathVariable String impUid)
            throws IamportResponseException, IOException {
        log.debug("PaymentController#paymentByImpUid called");
        log.debug("impUid={}", impUid);

        IamportResponse<Payment> response = iamportClient.paymentByImpUid(impUid);
        log.debug("IamportResponse={}", response);

        return ApiResponse.ok(response);
    }
}
