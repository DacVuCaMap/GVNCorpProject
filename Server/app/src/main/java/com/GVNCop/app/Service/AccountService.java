package com.GVNCop.app.Service;

import com.GVNCop.app.Entity.Account;
import com.GVNCop.app.Repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    public List<Account> getAll(){
        return accountRepository.findAll();
    }
    public void save(Account acc){
        accountRepository.save(acc);
    }
    public void updateAvt(String url,Long id){
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isPresent()){
            Account account = optionalAccount.get();
            account.setAvatar(url);
            accountRepository.save(account);
        }
        else{
            System.out.println("cannot find account with id:"+id);
        }
    }
    public Account getAccByEmail(String email){
        return accountRepository.findByEmail(email).get();
    }
}
