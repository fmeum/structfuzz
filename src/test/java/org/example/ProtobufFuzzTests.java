package org.example;

import com.code_intelligence.jazzer.junit.FuzzTest;
import com.code_intelligence.jazzer.mutation.annotation.NotNull;
import com.google.protobuf.DescriptorProtos.DescriptorProto;
import com.google.protobuf.DescriptorProtos.FileDescriptorProto;
import com.google.protobuf.Descriptors.DescriptorValidationException;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.DynamicMessage;
import com.google.protobuf.InvalidProtocolBufferException;

class ProtobufFuzzTests {

  @FuzzTest
  void dynamicMessageFuzzTest(@NotNull DescriptorProto messageType, byte @NotNull [] bytes) {
    FileDescriptorProto file = FileDescriptorProto.newBuilder()
        .setName("my_protos.proto")
        .addMessageType(messageType)
        .build();
    DynamicMessage message;
    try {
      message = DynamicMessage.getDefaultInstance(
          FileDescriptor.buildFrom(file, new FileDescriptor[0])
              .findMessageTypeByName(messageType.getName()));
    } catch (DescriptorValidationException ignored) {
      return;
    }
    try {
      message.getParserForType().parseFrom(bytes);
    } catch (InvalidProtocolBufferException ignored) {
    }
  }
}
