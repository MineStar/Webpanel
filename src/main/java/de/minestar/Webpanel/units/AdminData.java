/*
 * Copyright (C) 2014 MineStar.de 
 * 
 * This file is part of AdminStuff.
 * 
 * AdminStuff is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 * 
 * AdminStuff is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with AdminStuff.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.minestar.Webpanel.units;

import java.security.NoSuchAlgorithmException;

import de.minestar.Webpanel.utils.SHA;

public class AdminData {
    private final String userName;
    private final String passwordSalt;
    private final String saltedPassword;
    private final int hashCode;
    private final int level;

    public AdminData(String userName, String hashedPassword, String passwordSalt, int level) throws NoSuchAlgorithmException {
        this.userName = userName;
        this.passwordSalt = passwordSalt;
        this.saltedPassword = SHA.getHash(hashedPassword + this.passwordSalt);
        this.hashCode = (this.userName + ":" + this.saltedPassword).hashCode();
        this.level = level;
    }

    public boolean isPasswordCorrect(String hashedPassword) throws NoSuchAlgorithmException {
        return this.saltedPassword.equals(SHA.getHash(hashedPassword + this.passwordSalt));
    }

    public int getLevel() {
        return level;
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }
}
