package org.quickstep;

import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.junit.Test;
import org.quickstep.spec.CellSpec;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;
import static org.quickstep.GridBagToolKit.*;
import static org.quickstep.support.DebugSupport.gbcEquals;
import static org.quickstep.support.DebugSupport.gbcToString;

public class CellSpecTest
{
   public static final double DELTA = 1e-9;
   private CellSpec spec;

   @Test
   public void specFactoryMethodActsAsDefaultConstructor()
   {
      assertEquals(spec(), new CellSpec());
   }

   @Test
   public void testCopyConstructor()
   {
      spec = spec().withSizeGroup(0).withAnchor(AX.BOTH, AY.TOP).withBaseline().withInset(10).withGridSize(2, 1).withPreferredSize(100, 30).withIPad(2);
      CellSpec copy = new CellSpec(spec);
      assertEquals(spec, copy);
   }

   @Test
   public void deriveMethodCopiesSpec()
   {
      spec = spec().withSizeGroup(0).withAnchor(AX.BOTH, AY.TOP).withBaseline().withInset(10).withGridSize(2, 1).withPreferredSize(100, 30).withIPad(2);
      CellSpec copy = spec.derive();
      assertEquals(spec, copy);
      assertNotSame(spec, copy);
   }

   @Test
   public void overrideMethodOverwritesAllNotNullFields()
   {
      CellSpec spec1 = spec().withSizeGroup(0).withPreferredSize(200, 60).withGridWidth(3).withAnchor(AX.LEFT, AY.BOTTOM).withBaseline(false).withInsetY(5);
      CellSpec spec2 = spec().withSizeGroup(1).withPreferredSize(100, 30).withGridSize(2, 1).withAnchorY(AY.TOP).withBaseline().withInset(10).withIPad(2);
      CellSpec spec3 = spec();

      spec2.overrideWith(spec1);
      assertEquals(0, (int) spec2.getSizeGroupX());
      assertEquals(0, (int) spec2.getSizeGroupY());
      assertEquals(200, (int) spec2.getPreferredWidth());
      assertEquals(60, (int) spec2.getPreferredHeight());
      assertEquals(3, (int) spec2.getGridWidth());
      assertEquals(1, (int) spec2.getGridHeight());
      assertEquals(null, spec2.getWeightX());
      assertEquals(null, spec2.getWeightY());
      assertEquals(AX.LEFT, spec2.getAnchorX());
      assertEquals(AY.BOTTOM, spec2.getAnchorY());
      assertEquals(false, (boolean) spec2.getBaseline());
      assertEquals(5, (int) spec2.getInsetTop());
      assertEquals(10, (int) spec2.getInsetLeft());
      assertEquals(5, (int) spec2.getInsetBottom());
      assertEquals(10, (int) spec2.getInsetRight());
      assertEquals(2, (int) spec2.getIPadX());
      assertEquals(2, (int) spec2.getIPadY());

      CellSpec spec2Copy = spec2.derive();
      spec2.overrideWith(spec3);
      assertEquals(spec2Copy, spec2);
   }

   @Test
   public void overwriteMethodOverwritesAllFieldsEvenNull()
   {
      CellSpec spec1 = spec().withSizeGroup(1).withPreferredSize(100, 30).withGridSize(2, 1).withAnchorY(AY.TOP).withBaseline(false).withInset(10).withIPad(2);
      CellSpec spec2 = spec().withSizeGroup(2).withPreferredSize(200, 60).withGridSize(3, 4).withAnchorY(AY.BOTTOM).withBaseline(true).withInset(5).withIPad(3);
      CellSpec spec3 = spec();

      spec2.overwriteWith(spec1);
      assertEquals(spec1, spec2);

      spec2.overwriteWith(spec3);
      assertEquals(spec3, spec2);
   }

   @Test
   public void testSpecEqualsWithNull()
   {
      CellSpec spec = spec().withSizeGroup(1).withBaseline(false);
      //noinspection ObjectEqualsNull
      assertFalse(spec.equals(null));
   }

   @Test
   public void testSpecEquals()
   {
      CellSpec spec1 = spec().withSizeGroup(1).withPreferredSize(100, 30).withGridSize(2, 1).withAnchorY(AY.TOP).withBaseline(false).withInset(10).withIPad(2);
      CellSpec spec2 = spec().withSizeGroup(1).withPreferredSize(100, 30).withGridSize(2, 1).withAnchorY(AY.TOP).withBaseline(false).withInset(10).withIPad(2);
      CellSpec spec3 = spec().withSizeGroup(2).withPreferredSize(100, 30).withGridSize(2, 1).withAnchorY(AY.TOP).withBaseline(true).withInset(10).withIPad(88);
      assertTrue(spec1.equals(spec1));
      assertTrue(spec1.equals(spec2));
      assertFalse(spec1.equals(spec3));
   }

   @Test
   public void testGBCEqualForNullConstraints()
   {
      GridBagConstraints constraints = spec()
         .withSizeGroup(2).withPreferredSize(100, 30).withGridSize(2, 1).withAnchorY(AY.TOP).withBaseline(true).withInset(10).withIPad(2).toConstraints(1, 2);
      assertTrue(gbcEquals(null, null));
      assertFalse(gbcEquals(constraints, null));
      assertFalse(gbcEquals(null, constraints));
   }

   @Test
   public void testGBCEqualForNotNullConstraints()
   {
      GridBagConstraints constraints1 = spec()
         .withSizeGroup(2).withPreferredSize(100, 30).withGridSize(2, 1).withAnchorY(AY.TOP).withBaseline(true).withInset(10).withIPad(2).toConstraints(1, 2);
      GridBagConstraints constraints2 = spec()
         .withSizeGroup(2).withPreferredSize(100, 30).withGridSize(2, 1).withAnchorY(AY.TOP).withBaseline(true).withInset(10).withIPad(2).toConstraints(1, 2);
      GridBagConstraints constraints3 = spec()
         .withSizeGroup(2).withPreferredSize(100, 30).withGridSize(2, 1).withAnchorY(AY.TOP).withBaseline(true).withInset(10).withIPad(2).toConstraints(1, 3);
      assertTrue(gbcEquals(constraints1, constraints1));
      assertTrue(gbcEquals(constraints1, constraints2));
      assertFalse(gbcEquals(constraints1, constraints3));
   }

   @Test
   public void emptyGridBagSpecEvaluatesToDefaultGridBagConstraints()
   {
      GridBagConstraints constraintsFromSpec = spec().toConstraints(0, 0);
      assertEquals(1, constraintsFromSpec.gridwidth);
      assertEquals(1, constraintsFromSpec.gridheight);
      assertEquals(0.0, constraintsFromSpec.weightx, 1e-9);
      assertEquals(0.0, constraintsFromSpec.weighty, 1e-9);
      assertEquals(GridBagConstraints.CENTER, constraintsFromSpec.anchor);
      assertEquals(GridBagConstraints.NONE, constraintsFromSpec.fill);
      assertEquals(new Insets(0, 0, 0, 0), constraintsFromSpec.insets);
      assertEquals(0, constraintsFromSpec.ipadx);
      assertEquals(0, constraintsFromSpec.ipady);
   }

   @Test
   public void defaultGridBagSpecEvaluatesToDefaultGridBagConstraints()
   {
      GridBagConstraints constraintsFromSpec = completeSpec().toConstraints(0, 0);
      assertEquals(1, constraintsFromSpec.gridwidth);
      assertEquals(1, constraintsFromSpec.gridheight);
      assertEquals(0.0, constraintsFromSpec.weightx, 1e-9);
      assertEquals(0.0, constraintsFromSpec.weighty, 1e-9);
      assertEquals(GridBagConstraints.CENTER, constraintsFromSpec.anchor);
      assertEquals(GridBagConstraints.NONE, constraintsFromSpec.fill);
      assertEquals(new Insets(0, 0, 0, 0), constraintsFromSpec.insets);
      assertEquals(0, constraintsFromSpec.ipadx);
      assertEquals(0, constraintsFromSpec.ipady);
   }

   @Test
   public void testConstraintsFillFromSpecWithAnchor()
   {
      assertFill(spec().withAnchor(AX.LEFT, AY.TOP), GridBagConstraints.NONE);
      assertFill(spec().withAnchor(AX.LEFT, AY.CENTER), GridBagConstraints.NONE);
      assertFill(spec().withAnchor(AX.LEFT, AY.BOTTOM), GridBagConstraints.NONE);
      assertFill(spec().withAnchor(AX.CENTER, AY.BOTTOM), GridBagConstraints.NONE);
      assertFill(spec().withAnchor(AX.RIGHT, AY.BOTTOM), GridBagConstraints.NONE);
      assertFill(spec().withAnchor(AX.RIGHT, AY.CENTER), GridBagConstraints.NONE);
      assertFill(spec().withAnchor(AX.RIGHT, AY.TOP), GridBagConstraints.NONE);
      assertFill(spec().withAnchor(AX.CENTER, AY.TOP), GridBagConstraints.NONE);
      assertFill(spec().withAnchor(AX.CENTER, AY.CENTER), GridBagConstraints.NONE);

      assertFill(spec().withAnchor(AX.BOTH, AY.TOP), GridBagConstraints.HORIZONTAL);
      assertFill(spec().withAnchor(AX.BOTH, AY.CENTER), GridBagConstraints.HORIZONTAL);
      assertFill(spec().withAnchor(AX.BOTH, AY.BOTTOM), GridBagConstraints.HORIZONTAL);
      assertFill(spec().withAnchor(AX.LEFT, AY.BOTH), GridBagConstraints.VERTICAL);
      assertFill(spec().withAnchor(AX.CENTER, AY.BOTH), GridBagConstraints.VERTICAL);
      assertFill(spec().withAnchor(AX.RIGHT, AY.BOTH), GridBagConstraints.VERTICAL);
      assertFill(spec().withAnchor(AX.BOTH, AY.BOTH), GridBagConstraints.BOTH);

      assertFill(spec().withAnchor(AX.CENTER, AY.TOP), GridBagConstraints.NONE);
      assertFill(spec().withAnchor(AX.CENTER, AY.CENTER), GridBagConstraints.NONE);
      assertFill(spec().withAnchor(AX.CENTER, AY.BOTTOM), GridBagConstraints.NONE);
      assertFill(spec().withAnchor(AX.LEFT, AY.CENTER), GridBagConstraints.NONE);
      assertFill(spec().withAnchor(AX.CENTER, AY.CENTER), GridBagConstraints.NONE);
      assertFill(spec().withAnchor(AX.RIGHT, AY.CENTER), GridBagConstraints.NONE);
      assertFill(spec().withAnchor(AX.CENTER, AY.CENTER), GridBagConstraints.NONE);
   }

   private static void assertFill(CellSpec spec, int expectedFill)
   {
      GridBagConstraints constraints = spec.toConstraints(0, 0);
      assertEquals(expectedFill, constraints.fill);
   }

   @Test
   public void testConstraintsAnchorWeightFromSpecWithAnchor()
   {
      assertAnchor(spec().withAnchor(AX.LEFT, AY.TOP), GridBagConstraints.FIRST_LINE_START);
      assertAnchor(spec().withAnchor(AX.LEFT, AY.CENTER), GridBagConstraints.LINE_START);
      assertAnchor(spec().withAnchor(AX.LEFT, AY.BOTTOM), GridBagConstraints.LAST_LINE_START);
      assertAnchor(spec().withAnchor(AX.CENTER, AY.BOTTOM), GridBagConstraints.PAGE_END);
      assertAnchor(spec().withAnchor(AX.RIGHT, AY.BOTTOM), GridBagConstraints.LAST_LINE_END);
      assertAnchor(spec().withAnchor(AX.RIGHT, AY.CENTER), GridBagConstraints.LINE_END);
      assertAnchor(spec().withAnchor(AX.RIGHT, AY.TOP), GridBagConstraints.FIRST_LINE_END);
      assertAnchor(spec().withAnchor(AX.CENTER, AY.TOP), GridBagConstraints.PAGE_START);

      assertAnchor(spec().withAnchor(AX.BOTH, AY.TOP), GridBagConstraints.PAGE_START);
      assertAnchor(spec().withAnchor(AX.BOTH, AY.CENTER), GridBagConstraints.CENTER);
      assertAnchor(spec().withAnchor(AX.BOTH, AY.BOTTOM), GridBagConstraints.PAGE_END);
      assertAnchor(spec().withAnchor(AX.LEFT, AY.BOTH), GridBagConstraints.LINE_START);
      assertAnchor(spec().withAnchor(AX.CENTER, AY.BOTH), GridBagConstraints.CENTER);
      assertAnchor(spec().withAnchor(AX.RIGHT, AY.BOTH), GridBagConstraints.LINE_END);
      assertAnchor(spec().withAnchor(AX.BOTH, AY.BOTH), GridBagConstraints.CENTER);

      assertAnchor(spec().withAnchor(AX.CENTER, AY.TOP), GridBagConstraints.PAGE_START);
      assertAnchor(spec().withAnchor(AX.CENTER, AY.CENTER), GridBagConstraints.CENTER);
      assertAnchor(spec().withAnchor(AX.CENTER, AY.BOTTOM), GridBagConstraints.PAGE_END);
      assertAnchor(spec().withAnchor(AX.LEFT, AY.CENTER), GridBagConstraints.LINE_START);
      assertAnchor(spec().withAnchor(AX.CENTER, AY.CENTER), GridBagConstraints.CENTER);
      assertAnchor(spec().withAnchor(AX.RIGHT, AY.CENTER), GridBagConstraints.LINE_END);
      assertAnchor(spec().withAnchor(AX.CENTER, AY.CENTER), GridBagConstraints.CENTER);
   }

   @Test
   public void testConstraintsAnchorFromSpecWithAnchorAndBaseline()
   {
      assertAnchor(spec().withBaseline().withAnchor(AX.LEFT, AY.TOP), GridBagConstraints.FIRST_LINE_START);
      assertAnchor(spec().withBaseline().withAnchor(AX.LEFT, AY.CENTER), GridBagConstraints.BASELINE_LEADING);
      assertAnchor(spec().withBaseline().withAnchor(AX.LEFT, AY.BOTTOM), GridBagConstraints.LAST_LINE_START);
      assertAnchor(spec().withBaseline().withAnchor(AX.CENTER, AY.BOTTOM), GridBagConstraints.PAGE_END);
      assertAnchor(spec().withBaseline().withAnchor(AX.RIGHT, AY.BOTTOM), GridBagConstraints.LAST_LINE_END);
      assertAnchor(spec().withBaseline().withAnchor(AX.RIGHT, AY.CENTER), GridBagConstraints.BASELINE_TRAILING);
      assertAnchor(spec().withBaseline().withAnchor(AX.RIGHT, AY.TOP), GridBagConstraints.FIRST_LINE_END);
      assertAnchor(spec().withBaseline().withAnchor(AX.CENTER, AY.TOP), GridBagConstraints.PAGE_START);
      assertAnchor(spec().withBaseline().withAnchor(AX.CENTER, AY.CENTER), GridBagConstraints.BASELINE);

      assertAnchor(spec().withBaseline().withAnchor(AX.BOTH, AY.TOP), GridBagConstraints.PAGE_START);
      assertAnchor(spec().withBaseline().withAnchor(AX.BOTH, AY.CENTER), GridBagConstraints.BASELINE);
      assertAnchor(spec().withBaseline().withAnchor(AX.BOTH, AY.BOTTOM), GridBagConstraints.PAGE_END);
      assertAnchor(spec().withBaseline().withAnchor(AX.LEFT, AY.BOTH), GridBagConstraints.BASELINE_LEADING);
      assertAnchor(spec().withBaseline().withAnchor(AX.CENTER, AY.BOTH), GridBagConstraints.BASELINE);
      assertAnchor(spec().withBaseline().withAnchor(AX.RIGHT, AY.BOTH), GridBagConstraints.BASELINE_TRAILING);
      assertAnchor(spec().withBaseline().withAnchor(AX.BOTH, AY.BOTH), GridBagConstraints.BASELINE);

      assertAnchor(spec().withBaseline().withAnchor(AX.CENTER, AY.TOP), GridBagConstraints.PAGE_START);
      assertAnchor(spec().withBaseline().withAnchor(AX.CENTER, AY.CENTER), GridBagConstraints.BASELINE);
      assertAnchor(spec().withBaseline().withAnchor(AX.CENTER, AY.BOTTOM), GridBagConstraints.PAGE_END);
      assertAnchor(spec().withBaseline().withAnchor(AX.LEFT, AY.CENTER), GridBagConstraints.BASELINE_LEADING);
      assertAnchor(spec().withBaseline().withAnchor(AX.CENTER, AY.CENTER), GridBagConstraints.BASELINE);
      assertAnchor(spec().withBaseline().withAnchor(AX.RIGHT, AY.CENTER), GridBagConstraints.BASELINE_TRAILING);
      assertAnchor(spec().withBaseline().withAnchor(AX.CENTER, AY.CENTER), GridBagConstraints.BASELINE);
   }

   private static void assertAnchor(CellSpec spec, int expectedAnchor)
   {
      assertEquals(expectedAnchor, spec.toConstraints(0, 0).anchor);
   }

   @Test
   public void defaultGridBagSpecHasAllFieldsOtherThanPreferredSizeOrSizeGroupNotNull() throws IllegalAccessException
   {
      spec = completeSpec();
      for (Field field : CellSpec.class.getDeclaredFields())
      {
         if (isInstanceFieldAndNotPreferredSizeOrSizeGroup(field))
         {
            field.setAccessible(true);
            assertNotNull(field.getName(), field.get(spec));
         }
      }
   }

   @Test
   public void toConstraintsSetsGridXAndGridY()
   {
      GridBagConstraints constraints = spec().toConstraints(1, 2);
      assertEquals(1, constraints.gridx);
      assertEquals(2, constraints.gridy);
   }

   @Test
   public void testSpecToString()
   {
      spec = spec().withPreferredSize(200, 60).withGridWidth(3).withAnchorY(AY.BOTTOM).withInsetY(5);
      assertEquals("CellSpec{sizeGroup=*,* preferredSize=200,60 gridSize=3,* weight=*,* anchorX=* anchorY=BOTTOM " +
                      "baseline=* insets(top=5 left=* bottom=5 right=*) pad=*,*}", spec.toString());
   }

   @Test
   public void testConstraintsToString()
   {
      GridBagConstraints constraints = new GridBagConstraints(-1, 0, 1, GridBagConstraints.REMAINDER, 1.0, 2.0,
                                                              GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                                              new Insets(1, 2, 3, 4), 0, 10);
      assertEquals("GBC{grid=RELATIVE,0 gridsize=1,REMAINDER weight=1.0,2.0 anchor=CENTER fill=BOTH insets(top=1 left=2 bottom=3 right=4) ipad=0,10}",
                   gbcToString(constraints));
   }

   private boolean isInstanceFieldAndNotPreferredSizeOrSizeGroup(Field field)
   {
      return !"preferredWidth".equals(field.getName()) && !"preferredHeight".equals(field.getName())
         && !"sizeGroupX".equals(field.getName()) && !"sizeGroupY".equals(field.getName())
         && !Modifier.isStatic(field.getModifiers());
   }

   @Test
   public void sizeGroupAccessors()
   {
      spec = spec().withSizeGroupX(100);
      assertSizeGroup(100, true, spec.getSizeGroupX());
      assertSizeGroup(null, false, spec.getSizeGroupY());

      spec = spec().withSizeGroupY(200);
      assertSizeGroup(null, false, spec.getSizeGroupX());
      assertSizeGroup(200, true, spec.getSizeGroupY());

      spec = spec().withSizeGroup(100);
      assertSizeGroup(100, true, spec.getSizeGroupX());
      assertSizeGroup(100, true, spec.getSizeGroupY());

      spec = spec().withSizeGroup(100).withSizeGroupX(null).withSizeGroupY(null);
      assertSizeGroup(null, false, spec.getSizeGroupX());
      assertSizeGroup(null, false, spec.getSizeGroupY());

      spec = spec().withSizeGroup(100).withSizeGroup(null);
      assertSizeGroup(null, false, spec.getSizeGroupX());
      assertSizeGroup(null, false, spec.getSizeGroupY());

      spec = spec().withSizeGroup(100).withSizeGroupXUnassigned().withSizeGroupYUnassigned();
      assertNotNull(spec.getSizeGroupX());
      assertFalse(CellSpec.isSizeGroup(spec.getSizeGroupX()));
      assertNotNull(spec.getSizeGroupY());
      assertFalse(CellSpec.isSizeGroup(spec.getSizeGroupY()));

      spec = spec().withSizeGroup(100).withSizeGroupUnassigned();
      assertNotNull(spec.getSizeGroupX());
      assertFalse(CellSpec.isSizeGroup(spec.getSizeGroupX()));
      assertNotNull(spec.getSizeGroupY());
      assertFalse(CellSpec.isSizeGroup(spec.getSizeGroupY()));
   }

   private static void assertSizeGroup(Integer expectedId, boolean expectedIsSizeGroup, Integer actualId)
   {
      assertEquals(expectedId, actualId);
      assertEquals(expectedIsSizeGroup, CellSpec.isSizeGroup(actualId));
   }

   @Test
   public void preferredSizeAccessors()
   {
      spec = spec().withPreferredWidth(100);
      assertEquals(100, (int) spec.getPreferredWidth());
      assertNull(spec.getPreferredHeight());

      spec = spec().withPreferredHeight(200);
      assertNull(spec.getPreferredWidth());
      assertEquals(200, (int) spec.getPreferredHeight());

      spec = spec().withPreferredSize(100, 200);
      assertEquals(100, (int) spec.getPreferredWidth());
      assertEquals(200, (int) spec.getPreferredHeight());

      spec = spec().withPreferredHeight(200).withPreferredWidth(null).withPreferredHeight(null);
      assertNull(spec.getPreferredWidth());
      assertNull(spec.getPreferredHeight());
   }

   @Test
   public void gridSizeAccessors()
   {
      spec = spec().withGridWidth(2);
      assertEquals(2, (int) spec.getGridWidth());
      assertNull(spec.getGridHeight());

      spec = spec().withGridHeight(3);
      assertNull(spec.getGridWidth());
      assertEquals(3, (int) spec.getGridHeight());

      spec = spec().withGridSize(2, 3);
      assertEquals(2, (int) spec.getGridWidth());
      assertEquals(3, (int) spec.getGridHeight());

      spec = spec().withGridSize(2, 3).withGridWidth(null).withGridHeight(null);
      assertNull(spec.getGridWidth());
      assertNull(spec.getGridHeight());

      spec = spec().withGridWidthRemainder();
      assertEquals(GridBagConstraints.REMAINDER, (int) spec.getGridWidth());

      spec = spec().withGridHeightRemainder();
      assertEquals(GridBagConstraints.REMAINDER, (int) spec.getGridHeight());
   }

   @Test
   public void weightAccessors()
   {
      spec = spec().withWeightX(0.5);
      assertEquals(0.5, spec.getWeightX(), DELTA);
      assertNull(spec.getWeightY());

      spec = spec().withWeightY(1.5);
      assertNull(spec.getWeightX());
      assertEquals(1.5, spec.getWeightY(), DELTA);

      spec = spec().withWeight(0.5, 1.5);
      assertEquals(0.5, spec.getWeightX(), DELTA);
      assertEquals(1.5, spec.getWeightY(), DELTA);

      spec = spec().withWeightY(1.5).withWeightX(null).withWeightY(null);
      assertNull(spec.getWeightX());
      assertNull(spec.getWeightY());
   }

   @Test
   public void anchorAccessors()
   {
      spec = spec().withAnchorX(AX.LEFT);
      assertEquals(AX.LEFT, spec.getAnchorX());

      spec = spec().withAnchorY(AY.BOTTOM);
      assertEquals(AY.BOTTOM, spec.getAnchorY());

      spec = spec().withAnchor(A.BOTH);
      assertEquals(AX.BOTH, spec.getAnchorX());
      assertEquals(AY.BOTH, spec.getAnchorY());

      spec = spec().withAnchor(A.BOTH).withAnchorX(AX.LEFT);
      assertEquals(AX.LEFT, spec.getAnchorX());
      assertEquals(AY.BOTH, spec.getAnchorY());

      spec = spec().withAnchor(A.BOTH).withAnchorY(AY.BOTTOM);
      assertEquals(AX.BOTH, spec.getAnchorX());
      assertEquals(AY.BOTTOM, spec.getAnchorY());

      spec = spec().withAnchor(A.BOTH).withAnchorX(null).withAnchorY(null);
      assertNull(spec.getAnchorX());
      assertNull(spec.getAnchorY());
   }

   @Test
   public void baselineAccessors()
   {
      spec = spec().withBaseline();
      assertTrue(spec.getBaseline());

      spec = spec().withBaseline(true);
      assertTrue(spec.getBaseline());

      spec = spec().withBaseline(false);
      assertFalse(spec.getBaseline());

      spec.withBaseline(null);
      assertNull(spec.getBaseline());
   }

   @Test
   public void insetsAccessors()
   {
      spec = spec().withInsetTop(10).withInsetLeft(20).withInsetBottom(30).withInsetRight(40);
      assertEquals(10, (int) spec.getInsetTop());
      assertEquals(20, (int) spec.getInsetLeft());
      assertEquals(30, (int) spec.getInsetBottom());
      assertEquals(40, (int) spec.getInsetRight());

      spec.withInsetX(33);
      assertEquals(10, (int) spec.getInsetTop());
      assertEquals(33, (int) spec.getInsetLeft());
      assertEquals(30, (int) spec.getInsetBottom());
      assertEquals(33, (int) spec.getInsetRight());

      spec.withInsetY(11);
      assertEquals(11, (int) spec.getInsetTop());
      assertEquals(33, (int) spec.getInsetLeft());
      assertEquals(11, (int) spec.getInsetBottom());
      assertEquals(33, (int) spec.getInsetRight());

      spec.withInsetY(null);
      assertNull(spec.getInsetTop());
      assertEquals(33, (int) spec.getInsetLeft());
      assertNull(spec.getInsetBottom());
      assertEquals(33, (int) spec.getInsetRight());

      spec.withInsetRight(null);
      assertNull(spec.getInsetTop());
      assertEquals(33, (int) spec.getInsetLeft());
      assertNull(spec.getInsetBottom());
      assertNull(spec.getInsetRight());

      spec.withInset(6);
      assertEquals(6, (int) spec.getInsetTop());
      assertEquals(6, (int) spec.getInsetLeft());
      assertEquals(6, (int) spec.getInsetBottom());
      assertEquals(6, (int) spec.getInsetRight());

      spec.withInset(0, 1, 2, 3);
      assertEquals(0, (int) spec.getInsetTop());
      assertEquals(1, (int) spec.getInsetLeft());
      assertEquals(2, (int) spec.getInsetBottom());
      assertEquals(3, (int) spec.getInsetRight());

      spec.withInset(null);
      assertNull(spec.getInsetTop());
      assertNull(spec.getInsetLeft());
      assertNull(spec.getInsetBottom());
      assertNull(spec.getInsetRight());
   }

   @Test
   public void iPadAccessors()
   {
      spec = spec().withIPadX(10);
      assertEquals(10, (int) spec.getIPadX());
      assertNull(spec.getIPadY());

      spec = spec().withIPadY(20);
      assertNull(spec.getIPadX());
      assertEquals(20, (int) spec.getIPadY());

      spec = spec().withIPad(10, 20);
      assertEquals(10, (int) spec.getIPadX());
      assertEquals(20, (int) spec.getIPadY());

      spec = spec().withIPadY(20).withIPadX(null).withIPadY(null);
      assertNull(spec.getIPadX());
      assertNull(spec.getIPadY());
   }
}
