package com.b2wdigital.lets.juvenal.producer.validation.impl;

import com.b2wdigital.lets.juvenal.producer.exceptions.DictionaryValidationException;
import com.b2wdigital.lets.juvenal.producer.helper.DictionaryHelper;
import com.b2wdigital.lets.juvenal.producer.helper.EntityHelper;
import com.b2wdigital.lets.juvenal.producer.helper.MessageHelper;
import com.b2wdigital.lets.juvenal.producer.shared.EntityFieldError;
import com.b2wdigital.lets.juvenal.producer.validation.DictionaryValidator;
import com.fasterxml.jackson.databind.JsonNode;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class DictionaryValidatorImpl implements DictionaryValidator {
    @Inject
    Logger log;
    @Inject
    MessageHelper messageHelper;
    @Inject
    EntityHelper entityHelper;
    @Inject
    DictionaryHelper dictionaryHelper;

    @Override
    public List<EntityFieldError> validate(JsonNode message) throws DictionaryValidationException {
        List<EntityFieldError> errorList = new ArrayList<>();
        List<JsonNode> contents = new ArrayList<>();
        List<String> namespacesNotFound = new ArrayList<>();

        List<JsonNode> entities = messageHelper.groupEntities(message);
        String scope = messageHelper.getScope(message);

        entities.forEach(entity -> contents.add(entityHelper.getContent(entity, scope)));

        contents.forEach(content -> content.fields().forEachRemaining(namespace -> {
            String namespaceKey = namespace.getKey();

            if (!isDictionaryPresent(namespace)) {
                namespacesNotFound.add(namespaceKey);
            }else{
                List<String> invalidAttributes = isAttributeValid(content, namespace);
                if (!invalidAttributes.isEmpty()) {
                    log.errorf("result=error, op=validatePerNamespace, attributes not found in dictionary, namespace=%s, itens=%s", namespace.getKey(), invalidAttributes);
                    errorList.add(new EntityFieldError(invalidAttributes, namespace.getKey(), "attributes not found in dictionary"));
                }
            }
        }));

        if (!namespacesNotFound.isEmpty()) {
            errorList.add(new EntityFieldError(namespacesNotFound, "dictionary not found for the current namespace"));
            log.errorf("result=error, op=isDictionaryPresent, dictionaries not found for the current namespace, itens=%s", namespacesNotFound);
        }

        return errorList;
    }

    @Override
    public List<String> isAttributeValid(JsonNode scopeContent, Map.Entry<String, JsonNode> namespace) {
        List<String> unregisteredAttributes = new ArrayList<>();
        List<JsonNode> nameSpaceGroupTypeDictionaries = dictionaryHelper.getDictionariesForCurrentNamespaces(namespace);
        String namespaceKey = namespace.getKey();

        JsonNode dict = nameSpaceGroupTypeDictionaries.stream()
                .reduce((a, b) -> Integer.parseInt(String.valueOf(a.get("version"))) > Integer.parseInt(String.valueOf(b.get("version"))) ? a : b).get();

        final JsonNode attributesNamespace = scopeContent.get(namespaceKey);
        JsonNode dictContent = dict.get("content");

        attributesNamespace.fieldNames().forEachRemaining(value -> {
            if (!dictContent.has(value)) {
                unregisteredAttributes.add(value);
            }
        });

        return unregisteredAttributes;
    }

    @Override
    public boolean isDictionaryPresent(Map.Entry<String, JsonNode> namespace) {
        List<JsonNode> nameSpaceGroupTypeDictionaries = dictionaryHelper.getDictionariesForCurrentNamespaces(namespace);
        return !nameSpaceGroupTypeDictionaries.isEmpty();
    }
}

