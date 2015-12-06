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



/**
 * @class Represents an interval in time. Marks the start and the end plus the
 * periodicity.
 *
 * @author Marek Szanyi
 */
public class Period
{
    /**
     * @brief encoded value of a Period which is always on i.e 27/7
     */
    public static final int ALWAYS = 0x1FFFFFFF;

    /**
     * @brief encoded value of a zero Period
     */
    public static final int NEVER = 0x0;

    /**
     * @brief Days in a week
     */
    public enum Days
    {
        Monday(1 << 0),
        Tuesday(1 << 1),
        Wednesday(1 << 2),
        Thursday(1 << 3),
        Friday(1 << 4),
        Saturday(1 << 5),
        Sunday(1 << 6);

        private int code;

        Days(int code)
        {
            this.code = code;
        }

        public int getCode()
        {
            return code;
        }
    }

    private int mDays;
    private int mTimeStart;
    private int mTimeEnd;

    /**
     * @brief Constructor Create an empty Period
     */
    public Period()
    {
        mDays = mTimeEnd = mTimeStart = 0;
    }

    /**
     * @brief Constructor Create a Period class from an encoded value
     * @param encoded start + end time (in minutes) and days encoded in int
     */
    public Period(int encoded)
    {
        mDays = encoded & 0x7F;
        mTimeStart = (encoded >> 6) & 0x7FF;
        mTimeEnd = (encoded >> 17) & 0x7FF;
    }

    /**
     * @brief encode the actual values into a single int
     * @return an int which contains all information
     */
    public int encode()
    {
        int encoded = 0;
        encoded |= (mTimeEnd << 17);
        encoded |= (mTimeStart << 6);
        encoded |= mDays;

        return encoded;
    }

    /**
     * @brief check whenever a given day is enabled or disabled
     * @param aday enum of the day to check
     * @return true if the given day is enabled, false otherwise
     */
    public boolean isEnabledFor(Days aday)
    {
        return (mDays & aday.getCode()) == aday.getCode();
    }

    /**
     * @brief enable a given day
     * @param aday enum of the day to enable
     */
    public void enableFor(Days aday)
    {
        mDays |= aday.getCode();
    }

    public void disableFor(Days aday)
    {
        mDays ^= aday.getCode();
    }

    public void setTimeInterval(int timeInSecondsStart, int timeInSecondsEnd)
    {
        mTimeStart = timeInSecondsStart;
        mTimeEnd = timeInSecondsEnd;
    }

    public int getTimeStart()
    {
        return mTimeStart;
    }

    public int getTimeEnd()
    {
        return mTimeEnd;
    }

}

