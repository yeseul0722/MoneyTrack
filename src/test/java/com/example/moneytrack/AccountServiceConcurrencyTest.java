package com.example.moneytrack;

import com.example.moneytrack.domain.Account;
import com.example.moneytrack.domain.Member;
import com.example.moneytrack.dto.AccountCreateRequest;
import com.example.moneytrack.repository.AccountJpaRepository;
import com.example.moneytrack.repository.MemberJpaRepository;
import com.example.moneytrack.service.AccountService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AccountServiceConcurrencyTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountJpaRepository accountRepository;

    @Autowired
    private MemberJpaRepository memberRepository;

    @Autowired
    private EntityManager em;

    private Member testMember;

    @Test
    void 동시에_여러_계좌를_생성하면_계좌번호_중복이_발생하지_않는다() throws InterruptedException {

        Member testMember = new Member.Builder(
                    "testuser",
                    "password",
                    "홍길동",
                    LocalDate.parse("1998-09-12"))
                    .build();
        memberRepository.save(testMember);


        int threadCount = 10;
        int totalTasks = 10000;

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(totalTasks);

        List<String> createdAccountNumbers = new ArrayList<>();

        for (int i = 0; i < totalTasks; i++) {
            executorService.execute(() -> {
                try {
                    AccountCreateRequest request = new AccountCreateRequest("100", testMember.getId(), 10000L);

                    String accountNumber = accountService.createAccount(request).getAccountNumber();
                    createdAccountNumbers.add(accountNumber);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        // 중복 계좌번호가 있는지 확인
        long uniqueCount = createdAccountNumbers.stream().distinct().count();
        assertThat(uniqueCount).isEqualTo(totalTasks);


    }

}
