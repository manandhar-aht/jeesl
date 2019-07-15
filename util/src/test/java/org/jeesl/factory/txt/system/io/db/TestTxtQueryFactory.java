package org.jeesl.factory.txt.system.io.db;

import org.jeesl.AbstractJeeslUtilTest;
import org.jeesl.JeeslBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestTxtQueryFactory extends AbstractJeeslUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestTxtQueryFactory.class);
	
	
	public void cli()
	{
		String input="select meisprojec3_.id as col_0_0_, sum(meispwwork0_.daysFemale) as col_1_0_, sum(meispwwork0_.daysMale) as col_2_0_ from VupPwWorkingDays meispwwork0_ inner join VupPwPayrollOrder meispwpayr1_ on meispwwork0_.payroll_id=meispwpayr1_.id inner join VupPw meispublic2_ on meispwpayr1_.pw_id=meispublic2_.id inner join Project meisprojec3_ on meispublic2_.project_id=meisprojec3_.id where meisprojec3_.id in ($1 , $2 , $3 , $4 , $5 , $6 , $7 , $8 , $9 , $10 , $11 , $12 , $13 , $14 , $15 , $16 , $17 , $18 , $19 , $20 , $21 , $22 , $23 , $24 , $25 , $26 , $27 , $28 , $29 , $30 , $31 , $32 , $33 , $34 , $35 , $36 , $37 , $38 , $39 , $40 , $41 , $42) group by meisprojec3_.id";
		
		logger.info("Input: "+input);
		String output = TxtSqlQueryFactory.shortenIn(input);
		logger.info("Output: "+output);
	}
	
	public static void main (String[] args) throws Exception
	{
		JeeslBootstrap.init();
		TestTxtQueryFactory cli = new TestTxtQueryFactory();
		cli.cli();
	}
}