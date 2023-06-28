// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Predictions.proto

package com.tickers.io.protobuf;

public final class PredictionsProto {
  private PredictionsProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface PredictionsRequestOrBuilder extends
      // @@protoc_insertion_point(interface_extends:model.PredictionsRequest)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>string trainFilter = 1;</code>
     * @return The trainFilter.
     */
    java.lang.String getTrainFilter();
    /**
     * <code>string trainFilter = 1;</code>
     * @return The bytes for trainFilter.
     */
    com.google.protobuf.ByteString
        getTrainFilterBytes();

    /**
     * <code>string testFilter = 2;</code>
     * @return The testFilter.
     */
    java.lang.String getTestFilter();
    /**
     * <code>string testFilter = 2;</code>
     * @return The bytes for testFilter.
     */
    com.google.protobuf.ByteString
        getTestFilterBytes();

    /**
     * <code>string ticker_details_id = 3;</code>
     * @return The tickerDetailsId.
     */
    java.lang.String getTickerDetailsId();
    /**
     * <code>string ticker_details_id = 3;</code>
     * @return The bytes for tickerDetailsId.
     */
    com.google.protobuf.ByteString
        getTickerDetailsIdBytes();
  }
  /**
   * Protobuf type {@code model.PredictionsRequest}
   */
  public  static final class PredictionsRequest extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:model.PredictionsRequest)
      PredictionsRequestOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use PredictionsRequest.newBuilder() to construct.
    private PredictionsRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private PredictionsRequest() {
      trainFilter_ = "";
      testFilter_ = "";
      tickerDetailsId_ = "";
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new PredictionsRequest();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private PredictionsRequest(
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

              trainFilter_ = s;
              break;
            }
            case 18: {
              java.lang.String s = input.readStringRequireUtf8();

              testFilter_ = s;
              break;
            }
            case 26: {
              java.lang.String s = input.readStringRequireUtf8();

              tickerDetailsId_ = s;
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
      return com.tickers.io.protobuf.PredictionsProto.internal_static_model_PredictionsRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.tickers.io.protobuf.PredictionsProto.internal_static_model_PredictionsRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.tickers.io.protobuf.PredictionsProto.PredictionsRequest.class, com.tickers.io.protobuf.PredictionsProto.PredictionsRequest.Builder.class);
    }

    public static final int TRAINFILTER_FIELD_NUMBER = 1;
    private volatile java.lang.Object trainFilter_;
    /**
     * <code>string trainFilter = 1;</code>
     * @return The trainFilter.
     */
    public java.lang.String getTrainFilter() {
      java.lang.Object ref = trainFilter_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        trainFilter_ = s;
        return s;
      }
    }
    /**
     * <code>string trainFilter = 1;</code>
     * @return The bytes for trainFilter.
     */
    public com.google.protobuf.ByteString
        getTrainFilterBytes() {
      java.lang.Object ref = trainFilter_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        trainFilter_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int TESTFILTER_FIELD_NUMBER = 2;
    private volatile java.lang.Object testFilter_;
    /**
     * <code>string testFilter = 2;</code>
     * @return The testFilter.
     */
    public java.lang.String getTestFilter() {
      java.lang.Object ref = testFilter_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        testFilter_ = s;
        return s;
      }
    }
    /**
     * <code>string testFilter = 2;</code>
     * @return The bytes for testFilter.
     */
    public com.google.protobuf.ByteString
        getTestFilterBytes() {
      java.lang.Object ref = testFilter_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        testFilter_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int TICKER_DETAILS_ID_FIELD_NUMBER = 3;
    private volatile java.lang.Object tickerDetailsId_;
    /**
     * <code>string ticker_details_id = 3;</code>
     * @return The tickerDetailsId.
     */
    public java.lang.String getTickerDetailsId() {
      java.lang.Object ref = tickerDetailsId_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        tickerDetailsId_ = s;
        return s;
      }
    }
    /**
     * <code>string ticker_details_id = 3;</code>
     * @return The bytes for tickerDetailsId.
     */
    public com.google.protobuf.ByteString
        getTickerDetailsIdBytes() {
      java.lang.Object ref = tickerDetailsId_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        tickerDetailsId_ = b;
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
      if (!getTrainFilterBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, trainFilter_);
      }
      if (!getTestFilterBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, testFilter_);
      }
      if (!getTickerDetailsIdBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 3, tickerDetailsId_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!getTrainFilterBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, trainFilter_);
      }
      if (!getTestFilterBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, testFilter_);
      }
      if (!getTickerDetailsIdBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, tickerDetailsId_);
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
      if (!(obj instanceof com.tickers.io.protobuf.PredictionsProto.PredictionsRequest)) {
        return super.equals(obj);
      }
      com.tickers.io.protobuf.PredictionsProto.PredictionsRequest other = (com.tickers.io.protobuf.PredictionsProto.PredictionsRequest) obj;

      if (!getTrainFilter()
          .equals(other.getTrainFilter())) return false;
      if (!getTestFilter()
          .equals(other.getTestFilter())) return false;
      if (!getTickerDetailsId()
          .equals(other.getTickerDetailsId())) return false;
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
      hash = (37 * hash) + TRAINFILTER_FIELD_NUMBER;
      hash = (53 * hash) + getTrainFilter().hashCode();
      hash = (37 * hash) + TESTFILTER_FIELD_NUMBER;
      hash = (53 * hash) + getTestFilter().hashCode();
      hash = (37 * hash) + TICKER_DETAILS_ID_FIELD_NUMBER;
      hash = (53 * hash) + getTickerDetailsId().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.tickers.io.protobuf.PredictionsProto.PredictionsRequest parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.tickers.io.protobuf.PredictionsProto.PredictionsRequest parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.tickers.io.protobuf.PredictionsProto.PredictionsRequest parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.tickers.io.protobuf.PredictionsProto.PredictionsRequest parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.tickers.io.protobuf.PredictionsProto.PredictionsRequest parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.tickers.io.protobuf.PredictionsProto.PredictionsRequest parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.tickers.io.protobuf.PredictionsProto.PredictionsRequest parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.tickers.io.protobuf.PredictionsProto.PredictionsRequest parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.tickers.io.protobuf.PredictionsProto.PredictionsRequest parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static com.tickers.io.protobuf.PredictionsProto.PredictionsRequest parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.tickers.io.protobuf.PredictionsProto.PredictionsRequest parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.tickers.io.protobuf.PredictionsProto.PredictionsRequest parseFrom(
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
    public static Builder newBuilder(com.tickers.io.protobuf.PredictionsProto.PredictionsRequest prototype) {
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
     * Protobuf type {@code model.PredictionsRequest}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:model.PredictionsRequest)
        com.tickers.io.protobuf.PredictionsProto.PredictionsRequestOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.tickers.io.protobuf.PredictionsProto.internal_static_model_PredictionsRequest_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.tickers.io.protobuf.PredictionsProto.internal_static_model_PredictionsRequest_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.tickers.io.protobuf.PredictionsProto.PredictionsRequest.class, com.tickers.io.protobuf.PredictionsProto.PredictionsRequest.Builder.class);
      }

      // Construct using com.tickers.io.protobuf.PredictionsProto.PredictionsRequest.newBuilder()
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
        trainFilter_ = "";

        testFilter_ = "";

        tickerDetailsId_ = "";

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.tickers.io.protobuf.PredictionsProto.internal_static_model_PredictionsRequest_descriptor;
      }

      @java.lang.Override
      public com.tickers.io.protobuf.PredictionsProto.PredictionsRequest getDefaultInstanceForType() {
        return com.tickers.io.protobuf.PredictionsProto.PredictionsRequest.getDefaultInstance();
      }

      @java.lang.Override
      public com.tickers.io.protobuf.PredictionsProto.PredictionsRequest build() {
        com.tickers.io.protobuf.PredictionsProto.PredictionsRequest result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.tickers.io.protobuf.PredictionsProto.PredictionsRequest buildPartial() {
        com.tickers.io.protobuf.PredictionsProto.PredictionsRequest result = new com.tickers.io.protobuf.PredictionsProto.PredictionsRequest(this);
        result.trainFilter_ = trainFilter_;
        result.testFilter_ = testFilter_;
        result.tickerDetailsId_ = tickerDetailsId_;
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
        if (other instanceof com.tickers.io.protobuf.PredictionsProto.PredictionsRequest) {
          return mergeFrom((com.tickers.io.protobuf.PredictionsProto.PredictionsRequest)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.tickers.io.protobuf.PredictionsProto.PredictionsRequest other) {
        if (other == com.tickers.io.protobuf.PredictionsProto.PredictionsRequest.getDefaultInstance()) return this;
        if (!other.getTrainFilter().isEmpty()) {
          trainFilter_ = other.trainFilter_;
          onChanged();
        }
        if (!other.getTestFilter().isEmpty()) {
          testFilter_ = other.testFilter_;
          onChanged();
        }
        if (!other.getTickerDetailsId().isEmpty()) {
          tickerDetailsId_ = other.tickerDetailsId_;
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
        com.tickers.io.protobuf.PredictionsProto.PredictionsRequest parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.tickers.io.protobuf.PredictionsProto.PredictionsRequest) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private java.lang.Object trainFilter_ = "";
      /**
       * <code>string trainFilter = 1;</code>
       * @return The trainFilter.
       */
      public java.lang.String getTrainFilter() {
        java.lang.Object ref = trainFilter_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          trainFilter_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string trainFilter = 1;</code>
       * @return The bytes for trainFilter.
       */
      public com.google.protobuf.ByteString
          getTrainFilterBytes() {
        java.lang.Object ref = trainFilter_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          trainFilter_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string trainFilter = 1;</code>
       * @param value The trainFilter to set.
       * @return This builder for chaining.
       */
      public Builder setTrainFilter(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        trainFilter_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string trainFilter = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearTrainFilter() {
        
        trainFilter_ = getDefaultInstance().getTrainFilter();
        onChanged();
        return this;
      }
      /**
       * <code>string trainFilter = 1;</code>
       * @param value The bytes for trainFilter to set.
       * @return This builder for chaining.
       */
      public Builder setTrainFilterBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        trainFilter_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object testFilter_ = "";
      /**
       * <code>string testFilter = 2;</code>
       * @return The testFilter.
       */
      public java.lang.String getTestFilter() {
        java.lang.Object ref = testFilter_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          testFilter_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string testFilter = 2;</code>
       * @return The bytes for testFilter.
       */
      public com.google.protobuf.ByteString
          getTestFilterBytes() {
        java.lang.Object ref = testFilter_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          testFilter_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string testFilter = 2;</code>
       * @param value The testFilter to set.
       * @return This builder for chaining.
       */
      public Builder setTestFilter(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        testFilter_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string testFilter = 2;</code>
       * @return This builder for chaining.
       */
      public Builder clearTestFilter() {
        
        testFilter_ = getDefaultInstance().getTestFilter();
        onChanged();
        return this;
      }
      /**
       * <code>string testFilter = 2;</code>
       * @param value The bytes for testFilter to set.
       * @return This builder for chaining.
       */
      public Builder setTestFilterBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        testFilter_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object tickerDetailsId_ = "";
      /**
       * <code>string ticker_details_id = 3;</code>
       * @return The tickerDetailsId.
       */
      public java.lang.String getTickerDetailsId() {
        java.lang.Object ref = tickerDetailsId_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          tickerDetailsId_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string ticker_details_id = 3;</code>
       * @return The bytes for tickerDetailsId.
       */
      public com.google.protobuf.ByteString
          getTickerDetailsIdBytes() {
        java.lang.Object ref = tickerDetailsId_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          tickerDetailsId_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string ticker_details_id = 3;</code>
       * @param value The tickerDetailsId to set.
       * @return This builder for chaining.
       */
      public Builder setTickerDetailsId(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        tickerDetailsId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string ticker_details_id = 3;</code>
       * @return This builder for chaining.
       */
      public Builder clearTickerDetailsId() {
        
        tickerDetailsId_ = getDefaultInstance().getTickerDetailsId();
        onChanged();
        return this;
      }
      /**
       * <code>string ticker_details_id = 3;</code>
       * @param value The bytes for tickerDetailsId to set.
       * @return This builder for chaining.
       */
      public Builder setTickerDetailsIdBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        tickerDetailsId_ = value;
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


      // @@protoc_insertion_point(builder_scope:model.PredictionsRequest)
    }

    // @@protoc_insertion_point(class_scope:model.PredictionsRequest)
    private static final com.tickers.io.protobuf.PredictionsProto.PredictionsRequest DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.tickers.io.protobuf.PredictionsProto.PredictionsRequest();
    }

    public static com.tickers.io.protobuf.PredictionsProto.PredictionsRequest getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<PredictionsRequest>
        PARSER = new com.google.protobuf.AbstractParser<PredictionsRequest>() {
      @java.lang.Override
      public PredictionsRequest parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new PredictionsRequest(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<PredictionsRequest> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<PredictionsRequest> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.tickers.io.protobuf.PredictionsProto.PredictionsRequest getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_model_PredictionsRequest_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_model_PredictionsRequest_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\021Predictions.proto\022\005model\"X\n\022Prediction" +
      "sRequest\022\023\n\013trainFilter\030\001 \001(\t\022\022\n\ntestFil" +
      "ter\030\002 \001(\t\022\031\n\021ticker_details_id\030\003 \001(\tB+\n\027" +
      "com.tickers.io.protobufB\020PredictionsProt" +
      "ob\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_model_PredictionsRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_model_PredictionsRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_model_PredictionsRequest_descriptor,
        new java.lang.String[] { "TrainFilter", "TestFilter", "TickerDetailsId", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
