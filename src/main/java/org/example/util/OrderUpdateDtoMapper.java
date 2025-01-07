package org.example.util;

import org.example.data.model.goods.Address;
import org.example.data.model.goods.OrderItem;
import org.example.data.repositories.goods.AddressRepository;
import org.example.dto.response.goodsResponse.orderResponse.updateMapper.AddressDtoMapper;
import org.example.dto.response.goodsResponse.orderResponse.updateMapper.BuyerDtoMapper;
import org.example.dto.response.goodsResponse.orderResponse.updateMapper.OrderUpdateDto;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrderUpdateDtoMapper {
    private final AddressRepository addressRepository;

    public OrderUpdateDtoMapper(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public OrderUpdateDto mapOrderItem(OrderItem orderItem){
        OrderUpdateDto orderUpdateDto = new OrderUpdateDto();

        AddressDtoMapper addressDtoMapper = new AddressDtoMapper();
        if(orderItem.getAddress() != null) {
            Optional<Address> address = addressRepository.findById(orderItem.getAddress().getId());
            if(address.isEmpty()) {
                throw new IllegalArgumentException("Address not found,please update");
            }
            Address addressObj = address.get();
            addressObj.setCity(addressDtoMapper.getCity());
            addressObj.setStreet(addressDtoMapper.getStreet());
            addressObj.setHouseNumber(addressDtoMapper.getHouseNumber());
            orderItem.setAddress(addressObj);
        }

        BuyerDtoMapper buyerDtoMapper = new BuyerDtoMapper();
        if(orderItem.getBuyer() != null) {
            buyerDtoMapper.setContact(orderItem.getBuyer().getContact());
            buyerDtoMapper.setEmail(orderItem.getBuyer().getEmail());
        }

        orderItem.setAddress(orderItem.getAddress());
        orderUpdateDto.setBuyer(buyerDtoMapper);

        return orderUpdateDto;
    }
}
