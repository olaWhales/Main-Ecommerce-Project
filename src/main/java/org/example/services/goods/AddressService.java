package org.example.services.goods;

import org.example.dto.request.goodsRequest.AddressRequest;
import org.example.dto.response.goodsResponse.AddressResponse;
import org.springframework.stereotype.Service;

@Service
public interface AddressService {
    AddressResponse addressRegister(AddressRequest addressRequest);

}
