package software.amazon.awssdk.enhanced.dynamodb.extensions;

import java.time.Instant;
import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.enhanced.dynamodb.functionaltests.models.NestedRecordWithUpdateBehavior;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.HashMap;
import java.util.Map;

public class NestedRecordWithUpdateBehaviorAttributeConverter implements AttributeConverter<NestedRecordWithUpdateBehavior> {

    @Override
    public AttributeValue transformFrom(NestedRecordWithUpdateBehavior input) {
        if (input == null) {
            return AttributeValue.builder().nul(true).build();
        }

        Map<String, AttributeValue> map = new HashMap<>();
        map.put("id", AttributeValue.builder().s(input.getId()).build());
        map.put("nestedUpdateBehaviorAttribute", AttributeValue.builder().s(input.getNestedUpdateBehaviorAttribute()).build());
        map.put("nestedCounter", AttributeValue.builder().n(String.valueOf(input.getNestedCounter())).build());
        map.put("nestedTimeAttribute", AttributeValue.builder().s(input.getNestedTimeAttribute().toString()).build());

        return AttributeValue.builder().m(map).build();
    }

    @Override
    public NestedRecordWithUpdateBehavior transformTo(AttributeValue input) {
        if (input == null || input.m() == null) {
            return null;
        }

        Map<String, AttributeValue> map = input.m();
        NestedRecordWithUpdateBehavior nestedRecord = new NestedRecordWithUpdateBehavior();
        nestedRecord.setId(map.get("id").s());
        nestedRecord.setNestedUpdateBehaviorAttribute(map.get("nestedUpdateBehaviorAttribute").s());
        nestedRecord.setNestedCounter(Long.parseLong(map.get("nestedCounter").n()));
        nestedRecord.setNestedTimeAttribute(Instant.parse(map.get("nestedTimeAttribute").s()));

        return nestedRecord;
    }

    @Override
    public EnhancedType<NestedRecordWithUpdateBehavior> type() {
        return EnhancedType.of(NestedRecordWithUpdateBehavior.class);
    }

    @Override
    public AttributeValueType attributeValueType() {
        return AttributeValueType.M;
    }
}