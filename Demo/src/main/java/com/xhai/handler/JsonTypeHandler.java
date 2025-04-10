package com.xhai.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@MappedTypes(List.class)
public class JsonTypeHandler extends BaseTypeHandler<List<String>> {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final TypeReference<List<String>> TYPE_REF = new TypeReference<List<String>>() {};

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, toJson(parameter));
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return fromJson(rs.getString(columnName));
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return fromJson(rs.getString(columnIndex));
    }

    @Override
    public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return fromJson(cs.getString(columnIndex));
    }

    private String toJson(List<String> list) {
        try {
            return MAPPER.writeValueAsString(list);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> fromJson(String json) {
        try {
            return json == null ? null : MAPPER.readValue(json, TYPE_REF);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
} 