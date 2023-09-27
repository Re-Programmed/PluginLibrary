package com.willm.CoreMOD.Alloying;

public class AlloyUtils {

	
	public static int GetPickaxeCMD(AlloyMaterial mat1, AlloyMaterial mat2)
	{
		return mat2.Index * 10 + mat1.Index;
	}
	
	public static String GetPickaxeName(AlloyMaterial mat1, AlloyMaterial mat2)
	{
		return mat1.NameModifer + "-" + mat2.NameModifer + " Pickaxe";
	}
}
