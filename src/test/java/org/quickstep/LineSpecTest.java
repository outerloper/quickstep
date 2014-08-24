package org.quickstep;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.quickstep.GridBagToolKit.*;
import static org.quickstep.GridBagToolKit.specWithFill;
import static org.quickstep.GridBagToolKit.specWithFillY;

public class LineSpecTest
{
   LineSpec lineSpec = lineSpec();

   @Test
   public void equals()
   {
      lineSpec = lineSpec().
         specifyDefault(spec().withIPadX(5)).
         specifyCell(1, specWithFillX());
      LineSpec expected = lineSpec().
         specifyDefault(spec().withIPadX(5)).
         specifyCell(1, specWithFillX());
      assertEquals(expected, lineSpec);
   }

   @Test
   public void specsAreNotEqualWhenDefaultOrCellsAreSpecifiedDifferentlyEvenWhenForEachCellSpecReturnsTheSameSpec()
   {
      lineSpec = lineSpec().
         specifyDefault(spec().withIPadX(5)).
         specifyCell(1, specWithFillX());
      LineSpec expected = lineSpec().
         specifyDefault(spec().withIPadX(5)).
         specifyCell(1, specWithFillX()).
         specifyCell(2, spec().withIPadX(5));
      assertFalse(expected.equals(lineSpec));
   }

   @Test
   public void whenOverridingLineSpecThenDefaultsAndCellsAreOverridden()
   {
      lineSpec = lineSpec().
         specifyDefault(spec().withIPadX(5)).
         specifyCell(1, specWithFillX());
      LineSpec overriding = lineSpec().
         specifyDefault(spec().withIPadY(5)).
         specifyCell(1, specWithFillY()).
         specifyCell(2, specWithFillY());
      lineSpec.overrideWith(overriding);
      LineSpec expected = lineSpec().
         specifyDefault(spec().withIPad(5)).
         specifyCell(1, specWithFill()).
         specifyCell(2, specWithFillY());
      assertEquals(expected, lineSpec.overrideWith(overriding));
   }

   @Test
   public void whenOverridingLineSpecWithNullThenEqualSpecIsReturned()
   {
      lineSpec = lineSpec().
         specifyDefault(spec().withIPadX(5)).
         specifyCell(1, specWithFillX());
      LineSpec expected = lineSpec().
         specifyDefault(spec().withIPadX(5)).
         specifyCell(1, specWithFillX());
      assertEquals(expected, lineSpec.overrideWith(null));
   }
}
