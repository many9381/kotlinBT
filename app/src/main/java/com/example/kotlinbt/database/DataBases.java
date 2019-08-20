package com.example.kotlinbt.database;

import android.provider.BaseColumns;

/**
 * Created by parkkyounghyun
 */
public final class DataBases {

    public static final class CreateDB implements BaseColumns {
        public static final String IDENTIFICATION_NAME = "NAMES";
        public static final String IDENTIFICATION_NUM = "NUMS";

        public static final String TARGETCHECK = "CHECKT";
        public static final String _TABLENAME = "moduleinfo";
        public static final String _ID = "ID";
        // id name number time image
        public static final String _CREATE =
                "create table " + _TABLENAME + " ("
                        + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + IDENTIFICATION_NAME+" TEXT, "
                        + IDENTIFICATION_NUM+" INTEGER, "
                        + TARGETCHECK+" INTEGER)";

    }

}
