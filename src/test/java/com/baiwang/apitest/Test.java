package com.baiwang.apitest;

import java.io.File;

import org.testng.annotations.Parameters;

public class Test {
	@org.testng.annotations.Test
	@Parameters({"testFilePath","reslutFilePath"})
	public static void test() throws Exception{
		String testFile="Excel/fifteenth.xlsx";
		String resultFile="Result";
		Client client = new Client();
		client.run(testFile, resultFile);
		
	}
}
