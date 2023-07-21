package com.tickers.io.applicationapi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import io.jsonwebtoken.SigningKeyResolver;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.protobuf.ProtobufModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.google.protobuf.util.JsonFormat;
import org.springframework.http.converter.protobuf.ProtobufJsonFormatHttpMessageConverter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class GenericBeans {
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.registerModule(new ProtobufModule());
        mapper.getConfiguration()
                .setSkipNullEnabled(false)
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true);

//        mapper.addConverter(new AbstractConverter<ZonedDateTime, String>() {
//            @Override
//            protected String convert(ZonedDateTime zonedDateTime) {
//                if (zonedDateTime == null)
//                    return "";
//                return zonedDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
//            }
//        });
//        mapper.addConverter(new AbstractConverter<String, ZonedDateTime>() {
//            @Override
//            protected ZonedDateTime convert(String value) {
//                if (value == null)
//                    return null;
//                return ZonedDateTime.parse(value);
//            }
//        });
//
//        mapper.addConverter(new AbstractConverter<DicomCondition, RuleProtos.Condition.Builder>() {
//            @Override
//            protected RuleProtos.Condition.Builder convert(DicomCondition condition) {
//                return RuleProtos.Condition.newBuilder().setDicom(RuleProtos.DicomConditionLogic.newBuilder()
//                        .setValue(condition.getValue())
//                        .setOperator(condition.getOperator().toString())
//                        .setTag(condition.getTag()).build());
//            }
//        });
//        mapper.addConverter(new AbstractConverter<DayOfWeekCondition, RuleProtos.Condition.Builder>() {
//            @Override
//            protected RuleProtos.Condition.Builder convert(DayOfWeekCondition condition) {
//                return RuleProtos.Condition.newBuilder().setDayOfWeek(RuleProtos.DayOfWeekConditionLogic.newBuilder()
//                        .setTo(condition.getTo())
//                        .setFrom(condition.getFrom())
//                        .setValue(condition.getValue())
//                        .setOperator(condition.getOperator().toString())
//                        .setTimeZone(condition.getZoneId())
//                        .build()
//                );
//            }
//        });
//        mapper.addConverter(new AbstractConverter<TimeOfDayCondition, RuleProtos.Condition.Builder>() {
//            @Override
//            protected RuleProtos.Condition.Builder convert(TimeOfDayCondition condition) {
//                return RuleProtos.Condition.newBuilder().setTimeOfDay(RuleProtos.TimeOfDayConditionLogic.newBuilder()
//                        .setOperator(condition.getOperator().toString())
//                        .setTo(condition.getTo())
//                        .setFrom(condition.getFrom())
//                        .setValue(condition.getValue())
//                        .setTimeZone(condition.getZoneId())
//                        .build()
//                );
//            }
//        });
//        mapper.addConverter(new AbstractConverter<HL7Condition, RuleProtos.Condition.Builder>() {
//            @Override
//            protected RuleProtos.Condition.Builder convert(HL7Condition hl7Condition) {
//                return RuleProtos.Condition.newBuilder().setHl7(RuleProtos.HL7ConditionLogic.newBuilder()
//                        .setPath(hl7Condition.getPath())
//                        .setValue(hl7Condition.getValue())
//                        .setOperator(hl7Condition.getOperator().toString())
//                        .setFrom(hl7Condition.getFrom())
//                        .setTo(hl7Condition.getTo()));
//            }
//        });
//
//        mapper.addConverter(new AbstractConverter<ScriptCondition, RuleProtos.Condition.Builder>() {
//            @Override
//            protected RuleProtos.Condition.Builder convert(ScriptCondition scriptCondition) {
//                return RuleProtos.Condition.newBuilder().setScript(RuleProtos.ScriptConditionLogic.newBuilder()
//                        .setScript(scriptCondition.getScript()));
//            }
//        });
//
//        mapper.addConverter(new AbstractConverter<DynamicCondition, RuleProtos.Condition.Builder>() {
//            @Override
//            protected RuleProtos.Condition.Builder convert(DynamicCondition condition) {
//                return RuleProtos.Condition.newBuilder().setDynamic(RuleProtos.DynamicConditionLogic.newBuilder()
//                        .setValue(condition.getValue())
//                        .setOperator(condition.getOperator().toString())
//                        .setTag(condition.getTag()).build());
//            }
//        });
//
//        mapper.addConverter(new AbstractConverter<ConstantTransform, TransformProtos.Transform.Builder>() {
//            @Override
//            protected TransformProtos.Transform.Builder convert(ConstantTransform constantTransform) {
//                return TransformProtos.Transform.newBuilder().setConstant(TransformProtos.ConstantTransform.newBuilder()
//                        .setTag(constantTransform.getTag())
//                        .setValue(constantTransform.getValue())
//                );
//            }
//        });
//
//        mapper.addConverter(new AbstractConverter<RemoveTransform, TransformProtos.Transform.Builder>() {
//            @Override
//            protected TransformProtos.Transform.Builder convert(RemoveTransform removeTransform) {
//                return TransformProtos.Transform.newBuilder().setRemove(TransformProtos.RemoveTransform.newBuilder()
//                        .setTag(removeTransform.getTag())
//                );
//            }
//        });
//
//        mapper.addConverter(new AbstractConverter<RegexTransform, TransformProtos.Transform.Builder>() {
//            @Override
//            protected TransformProtos.Transform.Builder convert(RegexTransform regexTransform) {
//                return TransformProtos.Transform.newBuilder().setRegex(TransformProtos.RegexTransform.newBuilder()
//                        .setTag(regexTransform.getTag())
//                        .setRegex(regexTransform.getRegex())
//                        .setValue(regexTransform.getValue())
//                );
//            }
//        });
//
//        mapper.addConverter(new AbstractConverter<CopyTransform, TransformProtos.Transform.Builder>() {
//            @Override
//            protected TransformProtos.Transform.Builder convert(CopyTransform copyTransform) {
//                return TransformProtos.Transform.newBuilder().setCopy(TransformProtos.CopyTransform.newBuilder()
//                        .setSource(copyTransform.getSource())
//                        .setDestination(copyTransform.getDestination())
//                );
//            }
//        });
//
//        mapper.addConverter(new AbstractConverter<ScriptTransform, TransformProtos.Transform.Builder>() {
//            @Override
//            protected TransformProtos.Transform.Builder convert(ScriptTransform scriptTransform) {
//                return TransformProtos.Transform.newBuilder().setScript(TransformProtos.ScriptTransform.newBuilder()
//                        .setScript(scriptTransform.getScript())
//                );
//            }
//        });
//
//        mapper.addConverter(new AbstractConverter<AnonymizeTransform, TransformProtos.Transform.Builder>() {
//            @Override
//            protected TransformProtos.Transform.Builder convert(AnonymizeTransform anonymizeTransform) {
//                return TransformProtos.Transform.newBuilder().setAnonymize(
//                        TransformProtos.AnonymizeTransform.newBuilder()
//                                .setRetainDeviceIdentityOption(anonymizeTransform.isRetainDeviceIdentityOption())
//                                .setRetainUidsOption(anonymizeTransform.isRetainUidsOption())
//                                .setRetainInstitutionIdentityOption(anonymizeTransform.isRetainInstitutionIdentityOption())
//                                .setRetainLongitudinalTemporalInformationFullDatesOption(anonymizeTransform.isRetainLongitudinalTemporalInformationFullDatesOption())
//                                .build()
//                );
//            }
//        });

//        mapper.addConverter(new AbstractConverter<HL7ConstantTransform, TransformProtos.HL7Transform.Builder>() {
//            @Override
//            protected TransformProtos.HL7Transform.Builder convert(HL7ConstantTransform hl7ConstantTransform) {
//                return TransformProtos.HL7Transform.newBuilder().setConstant(TransformProtos.HL7ConstantTransform.newBuilder()
//                        .setPath(hl7ConstantTransform.getPath())
//                        .setValue(orBlank(hl7ConstantTransform.getValue()))
//                        .build()
//                );
//            }
//        });

//        mapper.addConverter(new AbstractConverter<HL7RemoveTransform, TransformProtos.HL7Transform.Builder>() {
//            @Override
//            protected TransformProtos.HL7Transform.Builder convert(HL7RemoveTransform removeTransform) {
//                return TransformProtos.HL7Transform.newBuilder()
//                        .setRemove(TransformProtos.HL7RemoveTransform.newBuilder()
//                                .setPath(removeTransform.getPath())
//                                .build()
//                        );
//            }
//        });

//        mapper.addConverter(new AbstractConverter<HL7RegexTransform, TransformProtos.HL7Transform.Builder>() {
//            @Override
//            protected TransformProtos.HL7Transform.Builder convert(HL7RegexTransform regexTransform) {
//                return TransformProtos.HL7Transform.newBuilder()
//                        .setRegex(TransformProtos.HL7RegexTransform.newBuilder()
//                                .setPath(regexTransform.getPath())
//                                .setRegex(regexTransform.getRegex())
//                                .setValue(orBlank(regexTransform.getValue()))
//                                .build()
//                        );
//            }
//        });

//        mapper.addConverter(new AbstractConverter<HL7CopyTransform, TransformProtos.HL7Transform.Builder>() {
//            @Override
//            protected TransformProtos.HL7Transform.Builder convert(HL7CopyTransform copyTransform) {
//                return TransformProtos.HL7Transform.newBuilder()
//                        .setCopy(TransformProtos.HL7CopyTransform.newBuilder()
//                                .setSourcePath(copyTransform.getSourcePath())
//                                .setDestinationPath(copyTransform.getDestinationPath())
//                                .build()
//                        );
//            }
//        });

//        mapper.addConverter(new AbstractConverter<HL7ScriptTransform, TransformProtos.HL7Transform.Builder>() {
//            @Override
//            protected TransformProtos.HL7Transform.Builder convert(HL7ScriptTransform scriptTransform) {
//                return TransformProtos.HL7Transform.newBuilder()
//                        .setScript(
//                                TransformProtos.HL7ScriptTransform.newBuilder()
//                                        .setScript(scriptTransform.getScript())
//                                        .build()
//                        );
//            }
//        });

//        mapper.addConverter(new AbstractConverter<DynamicTransform, TransformProtos.Transform.Builder>() {
//            @Override
//            protected TransformProtos.Transform.Builder convert(DynamicTransform dynamic) {
//                return TransformProtos.Transform.newBuilder().setDynamic(
//                        TransformProtos.DynamicTransform.newBuilder()
//                                .setValue(dynamic.getValue())
//                                .setOperator(dynamic.getOperator().toString())
//                                .setTag(dynamic.getTag())
//                                .setRelativeTo(dynamic.getRelativeTo().toString())
//                                .setTimeZone(dynamic.getZoneId())
//                );
//            }
//        });

//        mapper.addConverter(new AbstractConverter<Code, CodeSystemsProtos.CodeResponse.Builder>() {
//            @Override
//            protected CodeSystemsProtos.CodeResponse.Builder convert(Code code) {
//                CodeSystemsProtos.CodeResponse.Builder builder = CodeSystemsProtos.CodeResponse.newBuilder();
//                builder.setId(code.getId().toString());
//                mapIfNotNull(code::getCode, builder::setCode, v->v);
//                mapIfNotNull(code::getDisplay, builder::setDisplay, v->v);
//                CodingScheme codingScheme = code.getCodingScheme();
//                if(codingScheme != null)
//                {
//                    CodeSystemsProtos.CodingSystem.Builder codingSystemBuilder = CodeSystemsProtos.CodingSystem.newBuilder();
//                    codingSystemBuilder.setId(codingScheme.getId().toString());
//                    mapIfNotNull(codingScheme::getHl7Scheme, codingSystemBuilder::setHl7Scheme, v->v);
//                    mapIfNotNull(codingScheme::getHl7Version, codingSystemBuilder::setHl7Version, v->v);
//                    mapIfNotNull(codingScheme::getFhirSystem, codingSystemBuilder::setFhirSystem, v->v);
//                    mapIfNotNull(codingScheme::getFhirVersion, codingSystemBuilder::setFhirVersion, v->v);
//                    builder.setSystem(codingSystemBuilder);
//                }
//                return builder;
//            }
//        });

        return mapper;
    }

    @Bean
    public ProtobufJsonFormatHttpMessageConverter protobufJsonFormatHttpMessageConverter() {
        return new ProtobufJsonFormatHttpMessageConverter(JsonFormat.parser().ignoringUnknownFields(), JsonFormat.printer().includingDefaultValueFields());
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }
}
