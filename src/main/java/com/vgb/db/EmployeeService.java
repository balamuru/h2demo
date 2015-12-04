package com.vgb.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.BitSet;

@Service
public class EmployeeService {

    private static final String EMPLOYEE_TABLE = "admin.employee";

    @Autowired
    protected JdbcOperations jdbcOperations;

    @Autowired
    LobHandler lobHandler;

    public int deleteAll() throws Exception {
        return jdbcOperations.update(String.format("TRUNCATE TABLE %s", EMPLOYEE_TABLE));
    }

    public Employee create(final Employee employee) throws Exception {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final int updated = jdbcOperations.update(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection connection)
                            throws SQLException {
                        final PreparedStatement ps = connection.prepareStatement(String.format(
                                        "INSERT INTO %s (name, address, data) VALUES (?,?,?)",  EMPLOYEE_TABLE),
                                new String[]{"id"}
                        );
                        ps.setObject(1, employee.getName());
                        ps.setObject(2, employee.getAddress());
                        setBitSet(ps, 3, employee.getBitSet());

                        return ps;
                    }

                    private void setBitSet(PreparedStatement ps, int index, BitSet bitSet) throws SQLException {
                        if (bitSet != null) {
                            try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                                writeBitSetToOutputStream(bitSet, bos);
                                lobHandler.getLobCreator().setBlobAsBytes(ps, index, bos.toByteArray());
                            } catch (IOException e) {
                                throw new Error(e);
                            }
                        } else {
                            ps.setObject(index, null);
                        }
                    }
                },
                keyHolder
        );
        employee.setId(keyHolder.getKey().longValue());

        if (updated != 1) {
            throw new Exception("Failure to create " + employee);
        }

        return employee;

    }

    private static void writeBitSetToOutputStream(BitSet bits, OutputStream out) throws IOException {

        final DataOutputStream dataOut = out instanceof DataOutputStream ?
                (DataOutputStream) out : new DataOutputStream(out);
        dataOut.writeInt(bits.size());
        dataOut.writeInt(bits.cardinality());

        if (bits.size() <= Short.MAX_VALUE) {
            for (int i = bits.nextSetBit(0); i >= 0; i = bits.nextSetBit(i + 1)) {
                dataOut.writeShort(i);
            }
        } else {
            for (int i = bits.nextSetBit(0); i >= 0; i = bits.nextSetBit(i + 1)) {
                dataOut.writeInt(i);
            }
        }
    }


}
