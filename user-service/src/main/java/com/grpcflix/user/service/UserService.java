package com.grpcflix.user.service;

import com.grpcflix.user.repository.UserRepository;
import com.mkrzyszczyk.grpcflix.movie.Genre;
import com.mkrzyszczyk.grpcflix.user.UserGenreUpdateRequest;
import com.mkrzyszczyk.grpcflix.user.UserResponse;
import com.mkrzyszczyk.grpcflix.user.UserSearchRequest;
import com.mkrzyszczyk.grpcflix.user.UserServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.transaction.annotation.Transactional;


@GrpcService
@RequiredArgsConstructor
public class UserService extends UserServiceGrpc.UserServiceImplBase {

    private final UserRepository userRepository;

    @Override
    public void getUserGenre(UserSearchRequest request, StreamObserver<UserResponse> responseObserver) {
        UserResponse.Builder builder = UserResponse.newBuilder();
        this.userRepository.findById(request.getLoginId())
                .ifPresent(user -> {
                    builder.setLoginId(user.getLogin());
                    builder.setName(user.getName());
                    builder.setGenre(Genre.valueOf(user.getGenre().toUpperCase()));
                });
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    @Transactional
    public void updateUserGenre(UserGenreUpdateRequest request, StreamObserver<UserResponse> responseObserver) {
        UserResponse.Builder builder = UserResponse.newBuilder();
        this.userRepository.findById(request.getLoginId())
                .ifPresent(user -> {
                    user.setGenre(request.getGenre().name());
                    builder.setLoginId(user.getLogin());
                    builder.setName(user.getName());
                    builder.setGenre(Genre.valueOf(user.getGenre().toUpperCase()));
                });
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
