using System;
using System.Collections;
using System.Data;
using System.IO;
using System.Reflection;
using System.Configuration;
using IBatisNet.DataMapper.Configuration;
using log4net;

using NUnit.Framework;

using IBatisNet.DataAccess;

using IBatisNet.Common; // DataSource definition
using IBatisNet.Common.Utilities; // ScriptRunner definition
using IBatisNet.DataMapper; // SqlMap API

using IBatisNet.Common.Test.Domain;

[assembly:log4net.Config.XmlConfigurator(Watch=true)]

namespace IBatisNet.Common.Test.NUnit.CommonTests.Transaction
{
	/// <summary>
	/// Summary description for BaseTest.
	/// </summary>
	public abstract class BaseTest
	{
		/// <summary>
		/// The sqlMap
		/// </summary>
		protected static SqlMapper sqlMap;
		/// <summary>
		/// A daoManager with a SimpledaoSession
		/// </summary>
		protected static DaoManager daoManager = null;
		/// <summary>
		/// A daoManager with a sqlMapSession
		/// </summary>
		protected static DaoManager daoManagerSqlMap = null;

		private static readonly ILog _logger = LogManager.GetLogger( System.Reflection.MethodBase.GetCurrentMethod().DeclaringType );
		
		protected static string ScriptDirectory = null;

		/// <summary>
		/// Constructor
		/// </summary>
		static BaseTest()
		{

			ScriptDirectory = Path.Combine(
				Path.Combine(
				Path.Combine(
				Path.Combine(Resources.ApplicationBase, ".."), ".."), "Scripts"), ConfigurationSettings.AppSettings["database"]) + Path.DirectorySeparatorChar;
		}

		/// <summary>
		/// Initialize an sqlMap
		/// </summary>
		protected static void InitSqlMap()
		{
			//DateTime start = DateTime.Now;

			ConfigureHandler handler = new ConfigureHandler( Configure );
			DomSqlMapBuilder builder = new DomSqlMapBuilder();
			sqlMap = builder.ConfigureAndWatch( "sqlmap"+ "_" +  ConfigurationSettings.AppSettings["database"] + "_"
				+ ConfigurationSettings.AppSettings["providerType"] +".config", handler );

//			string loadTime = DateTime.Now.Subtract(start).ToString();
//			Console.WriteLine("Loading configuration time :"+loadTime);
		}

		/// <summary>
		/// Configure the SqlMap
		/// </summary>
		/// <remarks>
		/// Must verify ConfigureHandler signature.
		/// </remarks>
		/// <param name="obj">
		/// The reconfigured sqlMap.
		/// </param>
		protected static void Configure(object obj)
		{
			sqlMap = (SqlMapper)obj;
		}

		/// <summary>
		/// Run a sql batch for the datasource.
		/// </summary>
		/// <param name="datasource">The datasource.</param>
		/// <param name="script">The sql batch</param>
		protected static void InitScript(DataSource datasource, string script)
		{
			InitScript(datasource, script, true);
		}

		/// <summary>
		/// Run a sql batch for the datasource.
		/// </summary>
		/// <param name="datasource">The datasource.</param>
		/// <param name="script">The sql batch</param>
		/// <param name="doParse">parse out the statements in the sql script file.</param>
		protected static void InitScript(DataSource datasource, string script, bool doParse) {
			ScriptRunner runner = new ScriptRunner();

			runner.RunScript(datasource, script, doParse);
		}

		/// <summary>
		/// Create a new account with id = 6
		/// </summary>
		/// <returns>An account</returns>
		protected Account NewAccount6() 
		{
			Account account = new Account();
			account.Id = 6;
			account.FirstName = "Calamity";
			account.LastName = "Jane";
			account.EmailAddress = "no_email@provided.com";
			return account;
		}

		/// <summary>
		/// Verify that the input account is equal to the account(id=1).
		/// </summary>
		/// <param name="account">An account object</param>
		protected void AssertAccount1(Account account) 
		{
			Assert.AreEqual(1, account.Id, "account.Id");
			Assert.AreEqual("Joe", account.FirstName, "account.FirstName");
			Assert.AreEqual("Dalton", account.LastName, "account.LastName");
			Assert.AreEqual("Joe.Dalton@somewhere.com", account.EmailAddress, "account.EmailAddress");
		}

		/// <summary>
		/// Verify that the input account is equal to the account(id=1).
		/// </summary>
		/// <param name="account">An account as hashtable</param>
		protected void AssertAccount1AsHashtable(Hashtable account) 
		{
			Assert.AreEqual(1, (int)account["Id"], "account.Id");
			Assert.AreEqual("Joe", (string)account["FirstName"], "account.FirstName");
			Assert.AreEqual("Dalton", (string)account["LastName"], "account.LastName");
			Assert.AreEqual("Joe.Dalton@somewhere.com", (string)account["EmailAddress"], "account.EmailAddress");
		}

		/// <summary>
		/// Verify that the input account is equal to the account(id=6).
		/// </summary>
		/// <param name="account">An account object</param>
		protected void AssertAccount6(Account account) 
		{
			Assert.AreEqual(6, account.Id, "account.Id");
			Assert.AreEqual("Calamity", account.FirstName, "account.FirstName");
			Assert.AreEqual("Jane", account.LastName, "account.LastName");
			Assert.IsNull(account.EmailAddress, "account.EmailAddress");
		}



	}
}
