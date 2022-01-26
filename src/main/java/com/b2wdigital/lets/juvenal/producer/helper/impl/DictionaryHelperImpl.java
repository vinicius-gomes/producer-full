package com.b2wdigital.lets.juvenal.producer.helper.impl;

import com.b2wdigital.lets.juvenal.producer.helper.DictionaryHelper;
import com.b2wdigital.lets.juvenal.producer.service.DictionaryService;
import com.b2wdigital.lets.juvenal.producer.shared.Constants;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.tuple.Pair;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class DictionaryHelperImpl implements DictionaryHelper {

    @Inject
    DictionaryService dictionaryService;
    @Inject
    DictionaryHelper dictionaryHelper;

    @Override
    public List<JsonNode> getDictionariesForCurrentNamespaces(Map.Entry<String, JsonNode> namespace) {
        List<JsonNode> nameSpaceGroupTypeDictionaries = new ArrayList<>();

        String namespaceName = namespace.getKey();
        Pair<String, String> nameSpaceGroupType = dictionaryHelper.getNamespaces(namespaceName);

        dictionaryService.getDictionaries().iterator()
                .forEachRemaining(dictionary -> {
                    String dictNamespaceType = dictionary.get("namespaceType").toString().replace("\"", "");
                    String dictNamespaceGroup = dictionary.get("namespaceGroup").toString().replace("\"", "");
                    if (dictNamespaceType.equals(nameSpaceGroupType.getKey()) && dictNamespaceGroup.equals(nameSpaceGroupType.getValue())) {
                        nameSpaceGroupTypeDictionaries.add(dictionary);
                    }
                });

        return nameSpaceGroupTypeDictionaries;
    }

    @Override
    public Pair<String, String> getNamespaces(String namespaceName) {
        final String[] splitNamespace = namespaceName.split(Constants.NAMESPACE_DELIMITER);

        var entityNamespaceType = "";
        var entityNamespaceGroup = "";
        if (splitNamespace.length == 2) {
            entityNamespaceType = splitNamespace[0];
            entityNamespaceGroup = splitNamespace[1];
        } else {
            StringBuilder groups = null;
            for (var i = 1; i < splitNamespace.length; i++) {
                if (groups == null) {
                    groups = new StringBuilder(splitNamespace[i]);
                } else {
                    groups.append(Constants.WS_FILTER_SPLIT_FIELDS).append(splitNamespace[i]);
                }
            }
            entityNamespaceType = splitNamespace[0];
            entityNamespaceGroup = groups.toString();
        }
        return Pair.of(entityNamespaceType, entityNamespaceGroup);
    }
}
