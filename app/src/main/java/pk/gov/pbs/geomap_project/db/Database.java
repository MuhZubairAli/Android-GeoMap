package pk.gov.pbs.geomap_project.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import pk.gov.pbs.database.ModelBasedDatabaseHelper;
import pk.gov.pbs.database.exceptions.UnsupportedDataType;
import pk.gov.pbs.geomap_project.models.Location;
import pk.gov.pbs.geomap_project.models.Locations;

public class Database extends ModelBasedDatabaseHelper {
    private static final String dbName = "database.db";
    private static final int dbVersion = 8;
    private static Database INSTANCE;
    private final Class<?>[] SURVEY_MODELS = new Class[]{
            Locations.class, Location.class
    };

    private Database(Context context) {
        super(context, dbName, dbVersion);
    }

    public static Database getInstance(Context context){
        if (INSTANCE == null){
            synchronized (Database.class){
                INSTANCE = new Database(context);
            }
        }
        return INSTANCE;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            for (Class<?> model : SURVEY_MODELS){
                createTable(model, db);
            }
        } catch (UnsupportedDataType unsupportedDataType) {
            unsupportedDataType.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (Class<?> model : SURVEY_MODELS){
            dropTable(model, db);
        }
        onCreate(db);
    }

}
