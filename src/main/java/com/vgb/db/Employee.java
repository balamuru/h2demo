package com.vgb.db;

import java.io.Serializable;
import java.util.BitSet;

public class Employee implements Serializable {
    private Long id;
    private String name;
    private String address;
    private BitSet bitSet;

    public Employee(Long id, String name, String address, BitSet bitSet) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.bitSet = bitSet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BitSet getBitSet() {
        return bitSet;
    }

    public void setBitSet(BitSet bitSet) {
        this.bitSet = bitSet;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (address != null ? !address.equals(employee.address) : employee.address != null) return false;
        if (bitSet != null ? !bitSet.equals(employee.bitSet) : employee.bitSet != null) return false;
        if (id != null ? !id.equals(employee.id) : employee.id != null) return false;
        if (name != null ? !name.equals(employee.name) : employee.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (bitSet != null ? bitSet.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", bitSet of size=" + bitSet.size() +
                '}';
    }
}
