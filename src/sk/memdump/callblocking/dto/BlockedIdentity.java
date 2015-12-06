/*
 * Copyright (C) 2015 Marek Szanyi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sk.memdump.callblocking.dto;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @class Record is a DTO class which holds all the neccesary (and supplementary) data about
 * an identifier in a network which needs to be blocked.
 *
 * Created by Marek Szanyi
 */
public class BlockedIdentity implements Parcelable {

    /**
     * @brief A unique valid identifier of an entity in a network, this is specific to different
     * types of networks but always should be expressed by a String. For example telephone number
     * in GSM network.
     */
    private String mNetworkIdentifier;

    /**
     * @brief An Uri created by combining {@see ContactsContract.Contacts.CONTENT_LOOKUP_URI}
     * and a lookupKey. This allow us to find the related Contact in phone. If this value is
     * null this Record is not associated with any Contact.
     */
    private Uri mContactLookup;

    /**
     * @brief Specify when should be the identifier blocked. Based on the interval the identifier
     * is blocked periodically, always or never
     */
    private Period mBlockPeriod;



    /**
     * @brief Constructor to create an empty (invalid) Record object
     */
    public BlockedIdentity()
    {
        mNetworkIdentifier = null;
        mContactLookup = null;
        mBlockPeriod = null;
    }


    public String getNetworkIdentifier()
    {
        return mNetworkIdentifier;
    }

    public void setNetworkIdentifier(String networkIdentifier)
    {
        this.mNetworkIdentifier = networkIdentifier;
    }

    public Uri getContactLookup()
    {
        return mContactLookup;
    }

    public void setContactLookup(Uri contactLookup)
    {
        this.mContactLookup = mContactLookup;
    }

    public Period getBlockPeriod()
    {
        return mBlockPeriod;
    }

    public void setBlockPeriod(Period mBlockPeriod)
    {
        this.mBlockPeriod = mBlockPeriod;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mNetworkIdentifier);
        dest.writeParcelable(this.mContactLookup, 0);
        dest.writeParcelable(this.mBlockPeriod, flags);
    }

    protected BlockedIdentity(Parcel in) {
        this.mNetworkIdentifier = in.readString();
        this.mContactLookup = in.readParcelable(Uri.class.getClassLoader());
        this.mBlockPeriod = in.readParcelable(Period.class.getClassLoader());
    }

    public static final Parcelable.Creator<BlockedIdentity> CREATOR = new Parcelable.Creator<BlockedIdentity>() {
        public BlockedIdentity createFromParcel(Parcel source) {
            return new BlockedIdentity(source);
        }

        public BlockedIdentity[] newArray(int size) {
            return new BlockedIdentity[size];
        }
    };
}
