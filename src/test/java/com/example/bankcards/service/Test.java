package com.example.bankcards.service;

import com.example.bankcards.repository.TestRepo;
import org.springframework.beans.factory.annotation.Autowired;

public class Test {
    @Autowired
    private TestRepo testRepo;

    public void test() {
        testRepo.findById(1L);
    }

}
