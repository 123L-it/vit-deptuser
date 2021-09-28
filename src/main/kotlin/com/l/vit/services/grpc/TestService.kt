package com.l.vit.services.grpc



class TestService: TestGrpcKt.TestCoroutineImplBase() {

    override suspend fun validateUser(request: UserRequest) = UserReply
            .newBuilder()
            .setMessage("GRPC - Test")
            .build()
}
