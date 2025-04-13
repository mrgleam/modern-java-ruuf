package org.example;

import com.google.gson.Gson;
import redis.clients.jedis.UnifiedJedis;

import javax.swing.text.html.Option;
import java.util.Optional;

public class DocumentRepository {
    UnifiedJedis jedis;
    public DocumentRepository() {
        jedis = new UnifiedJedis("redis://localhost:6379");
        String simple = "{\"name\":\"Jordan\",\"email\":\"norman@futurestud.io\",\"age\":26,\"isDeveloper\":true}";
        jedis.set("norman@tuturestud.io", simple);
        String complicate = "{" +
                "\"name\":\"Jonathan\"," +
                "\"email\":\"jonathan@example.com\"," +
                "\"age\":30," +
                "\"isDeveloper\":true," +
                "\"field1\":\"value1\"," +
                "\"field2\":\"value2\"," +
                "\"field3\":\"value3\"," +
                "\"field4\":\"value4\"," +
                "\"field5\":\"value5\"" +
                "}";
        jedis.set("complicate@sample.com", complicate);
        String documentTransactionEntity = "{"
                + "\"caseNo\":\"CASE12345\","
                + "\"docType\":\"DOC_TYPE_CODE\","
                + "\"docClass\":\"DOC_CLASS_CODE\","
                + "\"destinationCompanyCode\":\"COMP123\","
                + "\"destinationOfficeCode\":\"OFFICE456\","
                + "\"totalDocument\":1,"
                + "\"docStatus\":\"NEW_DOCUMENT_STATUS\","
                + "\"hireeNo\":\"HIREENO789\","
                + "\"createdDate\":\"2025-04-11T10:20:30\","
                + "\"createdBy\":\"admin_user\""
                + "};";
        jedis.set("CASE12345", documentTransactionEntity);
    }

    public Optional<UserSimple> getUserSimpleById(String id){
        String userJson = jedis.get(id);
        if(userJson == null) return Optional.empty();
        Gson gson = new Gson();
        UserSimple userObject = gson.fromJson(userJson, UserSimple.class);
        return Optional.of(userObject);
    }

    public Optional<Boolean> saveUserComplicate(UserComplicate userComplicate) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(userComplicate);
        jedis.set(userComplicate.userSimple.email, jsonString);
        return Optional.of(true);
    }

    public Optional<Boolean> saveUserSimple(UserSimple userSimple) {
        jedis.set(userSimple.email, userSimple.email);
        return Optional.of(true);
    }

    public Optional<Boolean> saveDocumentTransactionEntity(DocumentTransactionEntity documentTransactionEntity) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(documentTransactionEntity);
        jedis.set(documentTransactionEntity.getCaseNo(), jsonString);
        return Optional.of(true);
    }

    public Optional<UserLegacy> getUserLegacyById(String id){
        String userJson = jedis.get(id);
        if(userJson == null) return Optional.empty();
        Gson gson = new Gson();
        UserLegacy userLegacy = gson.fromJson(userJson, UserLegacy.class);
        return Optional.of(userLegacy);
    }
}
