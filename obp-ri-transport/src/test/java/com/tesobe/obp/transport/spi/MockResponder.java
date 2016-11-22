/*
 * Copyright (c) TESOBE Ltd.  2016. All rights reserved.
 *
 * Use of this source code is governed by a GNU AFFERO license that can be found in the LICENSE file.
 *
 */
package com.tesobe.obp.transport.spi;

import com.tesobe.obp.transport.*;

import java.util.*;

import static com.tesobe.obp.util.ImplGen.generate;
import static java.util.Collections.emptyList;

/**
 * todo document
 */
public class MockResponder extends DefaultResponder
{
  @Override
  public Optional<Account> getAccount(Decoder.Pager p, Decoder.Parameters ps)
  {
    return ps
      .accountId()
      .map(id -> generate(Account.class, 1, "accountId", id));
  }

  @Override
  public List<Account> getAccounts(Decoder.Pager p, Decoder.Parameters ps)
  {
    return ps.bankId().map(bankId ->
    {
      List<Account> accounts = new ArrayList<>();

      accounts.add(generate(Account.class, 1, "bankId", bankId));
      accounts.add(generate(Account.class, 2, "bankId", bankId));

      return accounts;

    }).orElse(emptyList());
  }

  @Override
  public Optional<Bank> getBank(Decoder.Pager p, Decoder.Parameters ps)
  {
    return ps.bankId().map(id -> generate(Bank.class, 1, "bankId", id));
  }

  @Override public List<Bank> getBanks(Decoder.Pager p, Decoder.Parameters ps)
  {
    List<Bank> banks = new ArrayList<>();

    banks.add(generate(Bank.class, 1));
    banks.add(generate(Bank.class, 2));

    return banks;
  }

  @Override public Optional<Transaction> getTransaction(Decoder.Pager p,
    Decoder.Parameters ps)
  {
    return ps
      .transactionId()
      .map(id -> generate(Transaction.class, 1, "transactionId", id));
  }

  @Override public List<Transaction> getTransactions(Decoder.Pager pager,
    Decoder.Parameters ps)
  {
    List<Transaction> transactions = new ArrayList<>();

    transactions.add(generate(Transaction.class, 0));
    transactions.add(generate(Transaction.class, 1));

    return transactions;
  }

  @Override
  public Optional<User> getUser(Decoder.Pager p, Decoder.Parameters ps)
  {
    return ps.userId().map(id -> generate(User.class, 1, "email", id));
  }

  @Override
  public List<User> getUsers(Decoder.Pager pager, Decoder.Parameters ps)
  {
    List<User> users = new ArrayList<>();

    users.add(generate(User.class, 1));
    users.add(generate(User.class, 2));

    return users;
  }

  @Override public Token createTransaction(Decoder.Fields fs)
  {
    return generate(Token.class, 0, "id", Optional.of("tid-x"));
//    try
//    {
//      assertThat(fs.accountId(), isPresent());
//      assertThat(fs.amount(), isPresent());
//      assertThat(fs.currency(), isPresent());
//      assertThat(fs.otherAccountId(), isPresent());
//      assertThat(fs.otherAccountCurrency(), isPresent());
//      assertThat(fs.transactionType(), isPresent());
//      assertThat(fs.userId(), isPresent());
//
//      return Optional.of(generate(Token.class, 0, "id", "tid-x"));
//    }
//    catch(Error e)
//    {
//      return Optional.empty();
//    }
  }
}
