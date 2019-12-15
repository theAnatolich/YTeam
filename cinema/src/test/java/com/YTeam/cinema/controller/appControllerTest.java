package com.YTeam.cinema.controller;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;



import java.lang.reflect.Member;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.List;
import java.util.Map;


//@RunWith(Arquillian.class)
public class appControllerTest {
//    @Deployment
//    public static JavaArchive createDeployment() {
//        return ShrinkWrap.create(JavaArchive.class)
//                .addClass(appController.class)
//                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
//    }
    private appController appContr;

    @BeforeEach
    public void before() throws SQLException {
        appContr = new appController(true);
    }

    @Test
    void getAfishaTest()
    {
        Map<String, Object> model = null;
        String err = appContr.getAfisha("name", model);

        Assert.assertTrue("Test - getAfisha: Error!",err.equals("WEB-INF/pages/afisha"));
    }

    @Test
    void postAfishaTest()
    {
        String err = appContr.postAfisha(new Member() {
            @Override
            public Class<?> getDeclaringClass() {
                return null;
            }

            @Override
            public String getName() {
                return null;
            }

            @Override
            public int getModifiers() {
                return 0;
            }

            @Override
            public boolean isSynthetic() {
                return false;
            }
        });
        Assert.assertTrue("Test - postAfisha: Error!",err.equals("WEB-INF/pages/afisha"));
    }

    @Test
    void getAuthTest() throws SQLException, ParseException {
        Statement statMock = Mockito.mock(Statement.class);
        ResultSet resMock = Mockito.mock(ResultSet.class);

        Mockito.when(statMock.executeQuery("select * from get_films_shedule")).thenReturn(resMock);
        Mockito.when(resMock.next()).thenReturn(true, false);    // Нужно вернуть сначала true потом false
        Mockito.when(resMock.getInt(1)).thenReturn(1);
        Mockito.when(resMock.getInt(2)).thenReturn(1);
        Mockito.when(resMock.getInt(3)).thenReturn(1);
        Mockito.when(resMock.getString(4)).thenReturn("1");
        Mockito.when(resMock.getString(5)).thenReturn("1");
        Mockito.when(resMock.getString(6)).thenReturn("1");
        Mockito.when(resMock.getString(7)).thenReturn("1");
        Mockito.when(resMock.getString(8)).thenReturn("1");
        Mockito.when(resMock.getString(9)).thenReturn("1");
        Mockito.when(resMock.getString(10)).thenReturn("1999-01-01");
        Mockito.when(resMock.getString(11)).thenReturn("1");

        appContr.setStat(statMock);

        Map<String, Object> model = null;
        String err = appContr.getAuth("name",model);

        Assert.assertTrue("Test - getAuth: Error!",err.equals("WEB-INF/pages/afisha"));
    }


    @Test
    void getSeetSelectionTest() throws SQLException {
        Statement statMock = Mockito.mock(Statement.class);
        ResultSet resMock = Mockito.mock(ResultSet.class);

        String s="select id,shedule_id,plase_number,row_number,price,state from ticket  where shedule_id=1order by row_number,plase_number";

        Mockito.when(statMock.executeQuery(s)).thenReturn(resMock);
        Mockito.when(resMock.next()).thenReturn(true, false);    // Нужно вернуть сначала true потом false
        Mockito.when(resMock.getInt(1)).thenReturn(1);
        Mockito.when(resMock.getInt(2)).thenReturn(1);
        Mockito.when(resMock.getInt(3)).thenReturn(1);
        Mockito.when(resMock.getInt(4)).thenReturn(1);
        Mockito.when(resMock.getInt(5)).thenReturn(1);
        Mockito.when(resMock.getInt(6)).thenReturn(1);

        appContr.setStat(statMock);

        Map<String, Object> model = null;
        String err = appContr.getSeetSelection(1, model);

        Assert.assertTrue("Test - getSeetSelection: Error!",err.equals("WEB-INF/pages/SeetSelection"));
    }

}
