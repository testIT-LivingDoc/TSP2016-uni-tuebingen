/**
 * Copyright (c) 2008 Pyxis Technologies inc.
 * <p/>
 * This is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * <p/>
 * This software is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * <p/>
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin St, Fifth Floor, Boston, MA 02110-1301 USA, or see the FSF site:
 * http://www.fsf.org.
 */
package fixturedemo.bank;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class Bank {
    private final Map<String, AbstractBankAccount> accounts;

    public Bank() {
        accounts = new HashMap<String, AbstractBankAccount>();
    }

    public boolean hasAccount(String accountNumber) {
        return accounts.containsKey(accountNumber);
    }

    public AbstractBankAccount getAccount(String accountNumber) throws NoSuchAccountException {
        if (!hasAccount(accountNumber)) {
            throw new NoSuchAccountException(accountNumber);
        }
        return accounts.get(accountNumber);
    }

    public SavingsAccount openSavingsAccount(String number, Owner owner) {
        if (hasAccount(number)) {
            return null;
        }

        SavingsAccount account = new SavingsAccount(number, owner);
        accounts.put(number, account);
        return account;
    }

    public CheckingAccount openCheckingAccount(String number, Owner owner) {
        if (hasAccount(number)) {
            return null;
        }

        CheckingAccount account = new CheckingAccount(number, owner);
        accounts.put(number, account);
        return account;
    }

    public Money deposit(Money amount, String number) throws Exception {
        AbstractBankAccount account = accounts.get(number);
        return account.deposit(amount);
    }

    public Money withdraw(Money amount, String number, WithdrawType type) throws Exception {
        AbstractBankAccount account = accounts.get(number);
        return account.withdraw(amount, type);
    }

    public void freezeAccount(String number) {
        AbstractBankAccount account = accounts.get(number);
        account.freeze();
    }

    public Collection<AbstractBankAccount> getAccounts() {
        return Collections.unmodifiableCollection(accounts.values());
    }

    public void transfer(String numberFrom, String numberTo, Money amountToTransfert) throws Exception {
        if (!hasAccount(numberFrom)) {
            throw new NoSuchAccountException(numberFrom);
        }
        if (!hasAccount(numberTo)) {
            throw new NoSuchAccountException(numberTo);
        }

        AbstractBankAccount accountFrom = accounts.get(numberFrom);
        AbstractBankAccount accountTo = accounts.get(numberTo);

        if (accountFrom.getOwner().getFirstName().equals(accountTo.getOwner().getFirstName()) && accountFrom.getOwnerName()
            .equals(accountTo.getOwnerName())) {
            accountFrom.withdraw(amountToTransfert);
            accountTo.deposit(amountToTransfert);
        } else {
            throw new Exception("Can't transfer from not owned account !");
        }
    }
}
