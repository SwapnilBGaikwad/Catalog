package com.example.client;

import com.example.model.Product;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.examples.product.PriceCalculatorGrpc;
import io.grpc.examples.product.ProductReply;
import io.grpc.examples.product.ProductRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PriceComputationClient {
    private final ManagedChannel channel;
    private final PriceCalculatorGrpc.PriceCalculatorBlockingStub priceCalculatorStub;

    public PriceComputationClient() {
        this.channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();
        priceCalculatorStub = PriceCalculatorGrpc.newBlockingStub(channel);
    }

    public Double getTotalPrice(List<Product> products) {
        List<io.grpc.examples.product.Product> requestProducts = products.stream()
                .map(product -> io.grpc.examples.product.Product
                        .newBuilder()
                        .setName(product.getName())
                        .setPrice(product.getPrice().floatValue())
                        .build())
                .collect(Collectors.toList());
        ProductRequest productRequest = ProductRequest.newBuilder().addAllProducts(requestProducts).build();
        ProductReply productReply = priceCalculatorStub.calculateTotal(productRequest);
        return (double) productReply.getTotal();
    }
}
