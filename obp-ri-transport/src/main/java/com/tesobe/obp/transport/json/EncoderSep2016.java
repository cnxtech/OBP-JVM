/*
 * Copyright (c) TESOBE Ltd.  2016. All rights reserved.
 *
 * Use of this source code is governed by a GNU AFFERO license that can be found in the LICENSE file.
 *
 */
package com.tesobe.obp.transport.json;

import com.tesobe.obp.transport.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import static java.util.Objects.nonNull;

/**
 * Internal JSON encoder. Only called by trusted code.
 *
 * @since 2016.9
 */
@SuppressWarnings("WeakerAccess") public class EncoderSep2016 implements Encoder
{
  public EncoderSep2016(Transport.Version v)
  {
    version = v;
  }

  @Override public Request getAccount(String bankId, String accountId)
  {
    return request("get account").put("bank", bankId).put("account", accountId);
  }

  @Override
  public Request getAccount(String userId, String bankId, String accountId)
  {
    return request("get account")
      .put("user", userId)
      .put("bank", bankId)
      .put("account", accountId);
  }

  @Override public Request getAccounts(String bankId)
  {
    return request("get accounts").put("bank", bankId);
  }

  @Override public Request getAccounts(String userId, String bankId)
  {
    return request("get accounts").put("user", userId).put("bank", bankId);
  }

  @Override public Request getBank(String userId, String bankId)
  {
    return request("get bankOld").put("user", userId).put("bank", bankId);
  }

  @Override public Request getBank(String bankId)
  {
    return request("get bankOld").put("bank", bankId);
  }

  @Override public Request getBanks()
  {
    return request("get banks");
  }

  @Override public Request getBanks(String userId)
  {
    return request("get banks").put("user", userId);
  }

  @Override public Request getTransaction(String bankId, String accountId,
    String transactionId)
  {
    return request("get transaction")
      .put("bank", bankId)
      .put("account", accountId)
      .put("transaction", transactionId);
  }

  @Override public Request getTransaction(String bankId, String accountId,
    String transactionId, String userId)
  {
    return request("get transaction")
      .put("bank", bankId)
      .put("user", userId)
      .put("account", accountId)
      .put("transaction", transactionId);
  }

//  @Override public Request getTransactions(Connector.Pager p, String bankId,
//    String accountId)
//  {
//    return request("get transactions")
//      .put(p)
//      .put("bank", bankId)
//      .put("account", accountId);
//  }

//  @Override public Request getTransactions(Connector.Pager p, String bankId,
//    String accountId, String userId)
//  {
//    return request("get transactions")
//      .put(p)
//      .put("bank", bankId)
//      .put("account", accountId)
//      .put("user", userId);
//  }

  @Override public Request getUser(String userId)
  {
    return request("get user").put("user", userId);
  }

  @Override public Request getUsers(String userId)
  {
    return request("get users").put("user", userId);
  }

  @Override public Request getUsers()
  {
    return request("get users");
  }

  protected RequestBuilder request(String name)
  {
    return new RequestBuilder(name);
  }

  @Override public String account(Account a)
  {
    JSONObject json = json(a);

    return json != null ? json.toString() : notFound();
  }

  @Override public String accounts(List<? extends Account> accounts)
  {
    JSONArray result = new JSONArray();

    if(nonNull(accounts))
    {
      accounts.forEach(account -> json(account, result));
    }

    return result.toString();
  }

  @Override public String bank(Bank b)
  {
    JSONObject json = json(b);

    return json != null ? json.toString() : notFound();
  }

  @Override public String banks(List<? extends Bank> banks)
  {
    JSONArray result = new JSONArray();

    if(nonNull(banks))
    {
      banks.forEach(bank -> json(bank, result));
    }

    return result.toString();
  }

  protected JSONObject json(Account a)
  {
    if(nonNull(a))
    {
      return new AccountEncoder(a).toJson();
    }

    return null;
  }

  protected void json(Account a, JSONArray result)
  {
    if(nonNull(a))
    {
      JSONObject json = json(a);

      result.put(json != null ? json : JSONObject.NULL);
    }
    else
    {
      result.put(JSONObject.NULL);
    }
  }

  protected void json(Bank b, JSONArray result)
  {
    if(nonNull(b))
    {
      JSONObject json = json(b);

      result.put(json != null ? json : JSONObject.NULL);
    }
    else
    {
      result.put(JSONObject.NULL);
    }
  }

  protected JSONObject json(Bank b)
  {
    if(nonNull(b))
    {
      return new BankEncoder(b).toJson();
    }

    return null;
  }

  protected void json(Transaction t, JSONArray result)
  {
    if(nonNull(t))
    {
      JSONObject json = json(t);

      result.put(json != null ? json : JSONObject.NULL);
    }
    else
    {
      result.put(JSONObject.NULL);
    }
  }

  protected JSONObject json(Transaction t)
  {
    if(nonNull(t))
    {
      return new TransactionEncoder(t).toJson();
    }

    return null;
  }

  protected JSONObject json(User u)
  {
    if(nonNull(u))
    {
      return new UserEncoder(u).toJson();
    }

    return null;
  }

  protected void json(User u, JSONArray result)
  {
    if(nonNull(u))
    {
      JSONObject json = json(u);

      result.put(json != null ? json : JSONObject.NULL);
    }
    else
    {
      result.put(JSONObject.NULL);
    }
  }

  @Override public String transaction(Transaction t)
  {
    JSONObject json = json(t);

    return json != null ? json.toString() : JSONObject.NULL.toString();
  }

  @Override public String transactions(List<? extends Transaction> ts)
  {
    JSONArray result = new JSONArray();

    if(nonNull(ts))
    {
      ts.forEach(transaction -> json(transaction, result));
    }

    return result.toString();
  }

  @Override
  public String transactions(List<? extends Transaction> ts, boolean more)
  {
    if(more)
    {
      JSONArray data = new JSONArray();

      if(nonNull(ts))
      {
        ts.forEach(transaction -> json(transaction, data));
      }

      return new JSONObject().put("more", true).put("data", data).toString();
    }
    else
    {
      return transactions(ts);
    }
  }

  @Override public String user(User u)
  {
    JSONObject json = json(u);

    return json != null ? json.toString() : JSONObject.NULL.toString();
  }

  @Override public String transactionId(String s)
  {
    return s;
  }

  @Override public String users(List<? extends User> users)
  {
    JSONArray result = new JSONArray();

    if(nonNull(users))
    {
      users.forEach(user -> json(user, result));
    }

    return result.toString();
  }

  @Override public String token(Token t)
  {
    return notFound();
  }

  @Override public String error(String message)
  {
    return new JSONObject().put("error", message).toString();
  }

  public String notFound()
  {
    return JSONObject.NULL.toString();
  }

  @Override public String toString()
  {
    return getClass().getTypeName() + "-" + version;
  }

  final Transport.Version version;

  class RequestBuilder implements Request
  {
    public RequestBuilder(String name)
    {
      this.name = name;

      request.put("name", name);
      request.put("version", version);
    }

    @Override public String toString()
    {
      return request.toString();
    }

    public RequestBuilder put(String key, Map<?, ?> value)
    {
      if(value != null && !value.isEmpty())
      {
        request.put(key, value);
      }

      return this;
    }

    public RequestBuilder put(String key, String value)
    {
      if(value != null) // todo test for empty?
      {
        request.put(key, value);
      }

      return this;
    }

    public RequestBuilder put(String key, int value)
    {
      request.put(key, value);

      return this;
    }

    public RequestBuilder put(String key, JSONObject value)
    {
      request.put(key, value);

      return this;
    }

//    public RequestBuilder put(Connector.Pager p)
//    {
//      if(p instanceof ConnectorSep2016.DefaultPager)
//      {
//        ConnectorSep2016.DefaultPager pager
//          = ConnectorSep2016.DefaultPager.class.cast(p);
//
//        if(pager.offset != 0)
//        {
//          request.put("offset", pager.offset);
//        }
//
//        if(pager.size != 0)
//        {
//          request.put("size", pager.size);
//        }
//
//        if(nonNull(pager.field))
//        {
//          request.put("sort by", pager.field.toString());
//        }
//
//        if(nonNull(pager.sortOrder))
//        {
//          request.put("sort", pager.sortOrder.toString());
//        }
//
//        if(nonNull(pager.earliest))
//        {
//          request.put("earliest", Json.toJson(pager.earliest));
//        }
//
//        if(nonNull(pager.latest))
//        {
//          request.put("latest", Json.toJson(pager.latest));
//        }
//      }
//
//      return this;
//    }

    final String name;
    final JSONObject request = new JSONObject();
  }
}
