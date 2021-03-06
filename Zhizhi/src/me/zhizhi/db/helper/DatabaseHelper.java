package me.zhizhi.db.helper;

import java.sql.SQLException;
import java.util.List;

import me.zhizhi.db.constants.Cycle;
import me.zhizhi.db.constants.Lessions;
import me.zhizhi.db.constants.Week;
import me.zhizhi.db.entity.Academys;
import me.zhizhi.db.entity.Classes;
import me.zhizhi.db.entity.ClassesCurriculums;
import me.zhizhi.db.entity.Classrooms;
import me.zhizhi.db.entity.Courses;
import me.zhizhi.db.entity.CoursesTeachers;
import me.zhizhi.db.entity.Curriculums;
import me.zhizhi.db.entity.Teachers;
import me.zhizhi.db.entity.Titles;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private final static String TAG = DatabaseHelper.class.getSimpleName();

    // name of the database file for your application -- change to something
    // appropriate for your app
    public static final String DATABASE_NAME = "zhizhi.db";

    // any time you make changes to your database objects, you may have to
    // increase the database version
    private static final int DATABASE_VERSION = 1;

    private Dao<Teachers, Integer> mTeachersDao;

    private Dao<Titles, Integer> mTitlesDao;

    private Dao<Courses, Integer> mCoursesDao;

    private Dao<Curriculums, Integer> mCurriculumsDao;

    private Dao<Academys, Integer> mAcademysDao;

    private Dao<Classes, Integer> mClassesDao;

    private Dao<ClassesCurriculums, Integer> mClassesCurriculumsDao;

    private Dao<CoursesTeachers, Integer> mCoursesTeachersDao;

    private PreparedQuery<Curriculums> getCurriculumsQuery;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
        try {
            TableUtils.createTable(connectionSource, Academys.class);
            TableUtils.createTable(connectionSource, Classes.class);
            TableUtils.createTable(connectionSource, Classrooms.class);
            TableUtils.createTable(connectionSource, Curriculums.class);
            TableUtils.createTable(connectionSource, Courses.class);
            TableUtils.createTable(connectionSource, Teachers.class);
            TableUtils.createTable(connectionSource, Titles.class);
            TableUtils.createTable(connectionSource, ClassesCurriculums.class);
            TableUtils.createTable(connectionSource, CoursesTeachers.class);

            initDB();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2, int arg3) {
        try {
            TableUtils.dropTable(arg1, Academys.class, true);
            TableUtils.dropTable(arg1, Classes.class, true);
            TableUtils.dropTable(arg1, Classrooms.class, true);
            TableUtils.dropTable(arg1, Curriculums.class, true);
            TableUtils.dropTable(arg1, Courses.class, true);
            TableUtils.dropTable(arg1, Teachers.class, true);
            TableUtils.dropTable(arg1, Titles.class, true);
            TableUtils.dropTable(arg1, ClassesCurriculums.class, true);
            TableUtils.dropTable(arg1, CoursesTeachers.class, true);
            this.onCreate(arg0, arg1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Dao<Teachers, Integer> getTeachersDao() throws SQLException {
        if (mTeachersDao == null) {
            mTeachersDao = getDao(Teachers.class);
        }
        return mTeachersDao;
    }

    public Dao<Titles, Integer> getTitlesDao() throws SQLException {
        if (mTitlesDao == null) {
            mTitlesDao = getDao(Titles.class);
        }
        return mTitlesDao;
    }

    public Dao<Curriculums, Integer> getCurriculumsDao() throws SQLException {
        if (mCurriculumsDao == null) {
            mCurriculumsDao = getDao(Curriculums.class);
        }
        return mCurriculumsDao;
    }

    public Dao<Academys, Integer> getAcademysDao() throws SQLException {
        if (mAcademysDao == null) {
            mAcademysDao = getDao(Academys.class);
        }
        return mAcademysDao;
    }

    public Dao<Classes, Integer> getClassesDao() throws SQLException {
        if (mClassesDao == null) {
            mClassesDao = getDao(Classes.class);
        }
        return mClassesDao;
    }

    public Dao<ClassesCurriculums, Integer> getClassesCurriculumsDao() throws SQLException {
        if (mClassesCurriculumsDao == null) {
            mClassesCurriculumsDao = getDao(ClassesCurriculums.class);
        }
        return mClassesCurriculumsDao;
    }

    public Dao<CoursesTeachers, Integer> getCoursesTeachersDao() throws SQLException {
        if (mCoursesTeachersDao == null) {
            mCoursesTeachersDao = getDao(CoursesTeachers.class);
        }
        return mCoursesTeachersDao;
    }

    public Dao<Courses, Integer> getCoursesDao() throws SQLException {
        if (mCoursesDao == null) {
            mCoursesDao = getDao(Courses.class);
        }
        return mCoursesDao;
    }

    public List<Curriculums> getCurriculumsList(Classes _class) throws SQLException {
        if (getCurriculumsQuery == null) {
            getCurriculumsQuery = makeCurriculums(); //准备查询
        }
        getCurriculumsQuery.setArgumentHolderValue(0, _class); //设置参数
        return getCurriculumsDao().query(getCurriculumsQuery);//查询
    }

    private PreparedQuery<Curriculums> makeCurriculums() throws SQLException {
        QueryBuilder<ClassesCurriculums, Integer> ccQueryBuilder = getClassesCurriculumsDao()
                .queryBuilder();
        ccQueryBuilder.selectColumns(Curriculums.FOREIGN_ID);
        SelectArg userSelectArg = new SelectArg();
        ccQueryBuilder.where().eq(Classes.FOREIGN_ID, userSelectArg);

        QueryBuilder<Curriculums, Integer> curriculumsQB = getCurriculumsDao().queryBuilder();
        curriculumsQB.where().in(Curriculums.FOREIGN_ID, ccQueryBuilder);
        return curriculumsQB.prepare();
    }

    private void initDB() throws SQLException {
        Academys academy = new Academys("沧州师范学院全校课表(按班级)", "数学系", 2014, 2015, 0);
        getAcademysDao().create(academy);

        Titles t1 = new Titles("教授");
        getTitlesDao().create(t1);
        Titles fjc = new Titles("副教授");
        getTitlesDao().create(fjc);
        Titles js = new Titles("讲师");
        getTitlesDao().create(js);
        Titles zj = new Titles("助教");
        getTitlesDao().create(zj);

        Teachers gey = new Teachers("高恩勇", t1);
        getTeachersDao().create(gey);
        Teachers lbw = new Teachers("刘宝炜", fjc);
        getTeachersDao().create(lbw);
        Teachers wc = new Teachers("王  冲", js);
        getTeachersDao().create(wc);
        Teachers pyy = new Teachers("樊永艳", zj);
        getTeachersDao().create(pyy);

        Courses djtp = new Courses("点集拓扑");
        getCoursesDao().create(djtp);
        Courses yc = new Courses("运筹学");
        getCoursesDao().create(yc);
        Courses jhhb = new Courses("几何画板");
        getCoursesDao().create(jhhb);
        Courses xssy = new Courses("数学实验");
        getCoursesDao().create(xssy);

        CoursesTeachers yc_gey = new CoursesTeachers(yc, gey);
        getCoursesTeachersDao().create(yc_gey);
        CoursesTeachers jhhb_lbw = new CoursesTeachers(jhhb, lbw);
        getCoursesTeachersDao().create(jhhb_lbw);
        CoursesTeachers djtp_wc = new CoursesTeachers(djtp, wc);
        getCoursesTeachersDao().create(djtp_wc);
        CoursesTeachers xssy_zj = new CoursesTeachers(xssy, pyy);
        getCoursesTeachersDao().create(xssy_zj);

        Classes b11_1 = new Classes("B11数应-1", 41, academy);
        getClassesDao().create(b11_1);

        Curriculums b11_1_cur54 = new Curriculums(Week.Friday.getValue(),
                Lessions.Afternoon2.getValue(), Cycle.Weekly.getValue(), djtp_wc);
        getCurriculumsDao().create(b11_1_cur54);
        ClassesCurriculums cc_b11_1_cur54 = new ClassesCurriculums(b11_1, b11_1_cur54);
        getClassesCurriculumsDao().create(cc_b11_1_cur54);

        Curriculums cur44 = new Curriculums(Week.Thursday.getValue(),
                Lessions.Afternoon2.getValue(), Cycle.Weekly.getValue(), djtp_wc);
        getCurriculumsDao().create(cur44);
        ClassesCurriculums cc_b11_1_cur44 = new ClassesCurriculums(b11_1, cur44);
        getClassesCurriculumsDao().create(cc_b11_1_cur44);

        Curriculums b11_1_cur43 = new Curriculums(Week.Thursday.getValue(),
                Lessions.Afternoon1.getValue(), Cycle.Weekly.getValue(), xssy_zj);
        getCurriculumsDao().create(b11_1_cur43);
        ClassesCurriculums cc_b11_1_cur43 = new ClassesCurriculums(b11_1, b11_1_cur43);
        getClassesCurriculumsDao().create(cc_b11_1_cur43);

        Curriculums b11_1_cur42 = new Curriculums(Week.Thursday.getValue(),
                Lessions.Morning2.getValue(), Cycle.SingleWeekly.getValue(), jhhb_lbw);
        getCurriculumsDao().create(b11_1_cur42);
        ClassesCurriculums cc_b11_1_cur42 = new ClassesCurriculums(b11_1, b11_1_cur42);
        getClassesCurriculumsDao().create(cc_b11_1_cur42);

        Curriculums b11_1_cur34 = new Curriculums(Week.Wednesday.getValue(),
                Lessions.Afternoon2.getValue(), Cycle.Weekly.getValue(), xssy_zj);
        getCurriculumsDao().create(b11_1_cur34);
        ClassesCurriculums cc_b11_1_cur34 = new ClassesCurriculums(b11_1, b11_1_cur34);
        getClassesCurriculumsDao().create(cc_b11_1_cur34);

        Curriculums b11_1_cur32 = new Curriculums(Week.Wednesday.getValue(),
                Lessions.Morning2.getValue(), Cycle.Weekly.getValue(), jhhb_lbw);
        getCurriculumsDao().create(b11_1_cur32);
        ClassesCurriculums cc_b11_1_cur32 = new ClassesCurriculums(b11_1, b11_1_cur32);
        getClassesCurriculumsDao().create(cc_b11_1_cur32);

        Curriculums b11_1_cur24 = new Curriculums(Week.Tuesday.getValue(),
                Lessions.Afternoon2.getValue(), Cycle.Weekly.getValue(), yc_gey);
        getCurriculumsDao().create(b11_1_cur24);
        ClassesCurriculums cc_b11_1_cur24 = new ClassesCurriculums(b11_1, b11_1_cur24);
        getClassesCurriculumsDao().create(cc_b11_1_cur24);

        Curriculums b11_1_cur13 = new Curriculums(Week.Monday.getValue(),
                Lessions.Afternoon1.getValue(), Cycle.Weekly.getValue(), yc_gey);
        getCurriculumsDao().create(b11_1_cur13);
        ClassesCurriculums cc_b11_1_cur13 = new ClassesCurriculums(b11_1, b11_1_cur13);
        getClassesCurriculumsDao().create(cc_b11_1_cur13);

        Classes b11_2 = new Classes("B11数应-2", 41, academy);
        getClassesDao().create(b11_2);

        Classes b12 = new Classes("B12数应", 55, academy);
        getClassesDao().create(b12);

        Classes z12_1 = new Classes("Z12数教-1", 44, academy);
        getClassesDao().create(z12_1);

        Classes z12_2 = new Classes("Z12数教-2", 39, academy);
        getClassesDao().create(z12_2);

        Classes z12 = new Classes("Z12金融", 33, academy);
        getClassesDao().create(z12);

    }
}
