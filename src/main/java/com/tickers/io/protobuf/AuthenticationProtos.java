// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Authentication.proto

package com.tickers.io.protobuf;

public final class AuthenticationProtos {
  private AuthenticationProtos() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface CsrfResponseOrBuilder extends
      // @@protoc_insertion_point(interface_extends:model.CsrfResponse)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>string token = 1;</code>
     * @return The token.
     */
    java.lang.String getToken();
    /**
     * <code>string token = 1;</code>
     * @return The bytes for token.
     */
    com.google.protobuf.ByteString
        getTokenBytes();
  }
  /**
   * Protobuf type {@code model.CsrfResponse}
   */
  public  static final class CsrfResponse extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:model.CsrfResponse)
      CsrfResponseOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use CsrfResponse.newBuilder() to construct.
    private CsrfResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private CsrfResponse() {
      token_ = "";
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new CsrfResponse();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private CsrfResponse(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 10: {
              java.lang.String s = input.readStringRequireUtf8();

              token_ = s;
              break;
            }
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.tickers.io.protobuf.AuthenticationProtos.internal_static_model_CsrfResponse_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.tickers.io.protobuf.AuthenticationProtos.internal_static_model_CsrfResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.tickers.io.protobuf.AuthenticationProtos.CsrfResponse.class, com.tickers.io.protobuf.AuthenticationProtos.CsrfResponse.Builder.class);
    }

    public static final int TOKEN_FIELD_NUMBER = 1;
    private volatile java.lang.Object token_;
    /**
     * <code>string token = 1;</code>
     * @return The token.
     */
    public java.lang.String getToken() {
      java.lang.Object ref = token_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        token_ = s;
        return s;
      }
    }
    /**
     * <code>string token = 1;</code>
     * @return The bytes for token.
     */
    public com.google.protobuf.ByteString
        getTokenBytes() {
      java.lang.Object ref = token_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        token_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (!getTokenBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, token_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!getTokenBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, token_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof com.tickers.io.protobuf.AuthenticationProtos.CsrfResponse)) {
        return super.equals(obj);
      }
      com.tickers.io.protobuf.AuthenticationProtos.CsrfResponse other = (com.tickers.io.protobuf.AuthenticationProtos.CsrfResponse) obj;

      if (!getToken()
          .equals(other.getToken())) return false;
      if (!unknownFields.equals(other.unknownFields)) return false;
      return true;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + TOKEN_FIELD_NUMBER;
      hash = (53 * hash) + getToken().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.tickers.io.protobuf.AuthenticationProtos.CsrfResponse parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.tickers.io.protobuf.AuthenticationProtos.CsrfResponse parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.tickers.io.protobuf.AuthenticationProtos.CsrfResponse parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.tickers.io.protobuf.AuthenticationProtos.CsrfResponse parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.tickers.io.protobuf.AuthenticationProtos.CsrfResponse parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.tickers.io.protobuf.AuthenticationProtos.CsrfResponse parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.tickers.io.protobuf.AuthenticationProtos.CsrfResponse parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.tickers.io.protobuf.AuthenticationProtos.CsrfResponse parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.tickers.io.protobuf.AuthenticationProtos.CsrfResponse parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static com.tickers.io.protobuf.AuthenticationProtos.CsrfResponse parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.tickers.io.protobuf.AuthenticationProtos.CsrfResponse parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.tickers.io.protobuf.AuthenticationProtos.CsrfResponse parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(com.tickers.io.protobuf.AuthenticationProtos.CsrfResponse prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code model.CsrfResponse}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:model.CsrfResponse)
        com.tickers.io.protobuf.AuthenticationProtos.CsrfResponseOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.tickers.io.protobuf.AuthenticationProtos.internal_static_model_CsrfResponse_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.tickers.io.protobuf.AuthenticationProtos.internal_static_model_CsrfResponse_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.tickers.io.protobuf.AuthenticationProtos.CsrfResponse.class, com.tickers.io.protobuf.AuthenticationProtos.CsrfResponse.Builder.class);
      }

      // Construct using com.tickers.io.protobuf.AuthenticationProtos.CsrfResponse.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        token_ = "";

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.tickers.io.protobuf.AuthenticationProtos.internal_static_model_CsrfResponse_descriptor;
      }

      @java.lang.Override
      public com.tickers.io.protobuf.AuthenticationProtos.CsrfResponse getDefaultInstanceForType() {
        return com.tickers.io.protobuf.AuthenticationProtos.CsrfResponse.getDefaultInstance();
      }

      @java.lang.Override
      public com.tickers.io.protobuf.AuthenticationProtos.CsrfResponse build() {
        com.tickers.io.protobuf.AuthenticationProtos.CsrfResponse result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.tickers.io.protobuf.AuthenticationProtos.CsrfResponse buildPartial() {
        com.tickers.io.protobuf.AuthenticationProtos.CsrfResponse result = new com.tickers.io.protobuf.AuthenticationProtos.CsrfResponse(this);
        result.token_ = token_;
        onBuilt();
        return result;
      }

      @java.lang.Override
      public Builder clone() {
        return super.clone();
      }
      @java.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.setField(field, value);
      }
      @java.lang.Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @java.lang.Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @java.lang.Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return super.setRepeatedField(field, index, value);
      }
      @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedField(field, value);
      }
      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.tickers.io.protobuf.AuthenticationProtos.CsrfResponse) {
          return mergeFrom((com.tickers.io.protobuf.AuthenticationProtos.CsrfResponse)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.tickers.io.protobuf.AuthenticationProtos.CsrfResponse other) {
        if (other == com.tickers.io.protobuf.AuthenticationProtos.CsrfResponse.getDefaultInstance()) return this;
        if (!other.getToken().isEmpty()) {
          token_ = other.token_;
          onChanged();
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.tickers.io.protobuf.AuthenticationProtos.CsrfResponse parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.tickers.io.protobuf.AuthenticationProtos.CsrfResponse) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private java.lang.Object token_ = "";
      /**
       * <code>string token = 1;</code>
       * @return The token.
       */
      public java.lang.String getToken() {
        java.lang.Object ref = token_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          token_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string token = 1;</code>
       * @return The bytes for token.
       */
      public com.google.protobuf.ByteString
          getTokenBytes() {
        java.lang.Object ref = token_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          token_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string token = 1;</code>
       * @param value The token to set.
       * @return This builder for chaining.
       */
      public Builder setToken(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        token_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string token = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearToken() {
        
        token_ = getDefaultInstance().getToken();
        onChanged();
        return this;
      }
      /**
       * <code>string token = 1;</code>
       * @param value The bytes for token to set.
       * @return This builder for chaining.
       */
      public Builder setTokenBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        token_ = value;
        onChanged();
        return this;
      }
      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:model.CsrfResponse)
    }

    // @@protoc_insertion_point(class_scope:model.CsrfResponse)
    private static final com.tickers.io.protobuf.AuthenticationProtos.CsrfResponse DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.tickers.io.protobuf.AuthenticationProtos.CsrfResponse();
    }

    public static com.tickers.io.protobuf.AuthenticationProtos.CsrfResponse getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<CsrfResponse>
        PARSER = new com.google.protobuf.AbstractParser<CsrfResponse>() {
      @java.lang.Override
      public CsrfResponse parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new CsrfResponse(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<CsrfResponse> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<CsrfResponse> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.tickers.io.protobuf.AuthenticationProtos.CsrfResponse getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_model_CsrfResponse_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_model_CsrfResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\024Authentication.proto\022\005model\"\035\n\014CsrfRes" +
      "ponse\022\r\n\005token\030\001 \001(\tB/\n\027com.tickers.io.p" +
      "rotobufB\024AuthenticationProtosb\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_model_CsrfResponse_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_model_CsrfResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_model_CsrfResponse_descriptor,
        new java.lang.String[] { "Token", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
