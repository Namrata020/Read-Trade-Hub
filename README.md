//Model --> Employee.cs
namespace APP.Emp;
public class Employee
{
    public int EmployeeID{get; set;}
    public string Name{get; set;}
    public double Salary{get; set;}
    public int DepartmentId{get; set;}
}
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//Service --> Connected.cs
using System.Data;
using System.Data.Common;
using APP.Emp;
using MySql.Data.MySqlClient;
namespace APP.Data;

public class MySqlData{
     MySqlConnection connection;
     
    public MySqlData() {
        connection= new (@"server=localhost; port=3306; user=root; password=root123; database=test;");
    }
    public List<Employee> GetAll(){

        MySqlCommand cmd= new MySqlCommand("select * from employeesend");
        cmd.Connection=connection;
        List<Employee> employees=new List<Employee>();
        try
        {
            DataSet ds= new DataSet();
            MySqlDataAdapter sqlda= new MySqlDataAdapter(cmd);
            MySqlCommandBuilder cd=new MySqlCommandBuilder(sqlda);
            sqlda.Fill(ds);
            DataTable dt=ds.Tables[0];
            DataRowCollection rows= dt.Rows;
            foreach (DataRow r in rows)
            {
                int id=int.Parse(r["EmployeeId"].ToString());
                string name=r["name"].ToString();
                double sal=double.Parse(r["salary"].ToString());
                int dept=int.Parse(r["DepartmentId"].ToString());
                employees.Add(new Employee{EmployeeID=id,Name=name,Salary=sal,DepartmentId=dept});
            }
        }
        catch (Exception e)
        {
            Console.WriteLine(e.Message);
        }
        return employees;
    }

    public Employee? GetByID(int eid){

        MySqlCommand cmd= new MySqlCommand("select * from employeesend");
        cmd.Connection=connection;
        Employee? employees=null;
        try
        {
            Console.WriteLine(eid);
            DataSet ds= new DataSet();
            MySqlDataAdapter sqlda= new MySqlDataAdapter(cmd);
            sqlda.Fill(ds);
            DataTable dt=ds.Tables[0];
            DataRowCollection rows= dt.Rows;
            DataRow[] d =dt.Select("EmployeeId="+eid);
            DataRow r=d[0];
                int id=int.Parse(r["EmployeeId"].ToString());
                string name=r["name"].ToString();
                double sal=double.Parse(r["salary"].ToString());
                int dept=int.Parse(r["DepartmentId"].ToString());
                employees=new Employee{EmployeeID=id,Name=name,Salary=sal,DepartmentId=dept};
            Console.WriteLine(employees.Name);
        }
        catch (Exception e)
        {
            Console.WriteLine("hello "+e.Message);
        }
        return employees;
    }

    public void Edit(Employee emp){

        MySqlCommand cmd= new MySqlCommand("select * from employeesend");
        cmd.Connection=connection;
        try
        {
            Console.WriteLine(emp.EmployeeID);
            DataSet ds= new DataSet();
            MySqlDataAdapter sqlda= new MySqlDataAdapter(cmd);
            MySqlCommandBuilder cd=new MySqlCommandBuilder(sqlda);
            sqlda.Fill(ds);
            DataTable dt=ds.Tables[0];
            DataRowCollection rows= dt.Rows;
            DataRow[] d =dt.Select("EmployeeId="+emp.EmployeeID);
            DataRow r=d[0];
            r["EmployeeId"]=emp.EmployeeID;
            r["name"]=emp.Name;
            r["salary"]=emp.Salary;
            r["DepartmentId"]=emp.DepartmentId;
            Console.WriteLine(""+emp.DepartmentId);
               // employees=new Employee{EmployeeID=id,Name=name,Salary=sal,DepartmentId=dept};
            sqlda.Update(ds);
        }
        catch (Exception e)
        {
            Console.WriteLine("hello "+e.Message);
        }
    }

    public void Add(Employee emp){

        MySqlCommand cmd= new MySqlCommand("select * from employeesend");
        cmd.Connection=connection;
        try
        {
            Console.WriteLine(emp.EmployeeID);
            DataSet ds= new DataSet();
            MySqlDataAdapter sqlda= new MySqlDataAdapter(cmd);
            MySqlCommandBuilder cd=new MySqlCommandBuilder(sqlda);
            sqlda.Fill(ds);
            DataTable dt=ds.Tables[0];
            DataRow r=dt.NewRow();
            r["EmployeeId"]=emp.EmployeeID;
            r["name"]=emp.Name;
            r["salary"]=emp.Salary;
            r["DepartmentId"]=emp.DepartmentId;

            dt.Rows.Add(r);
            Console.WriteLine(""+emp.DepartmentId);
               // employees=new Employee{EmployeeID=id,Name=name,Salary=sal,DepartmentId=dept};
            sqlda.Update(ds);
        }
        catch (Exception e)
        {
            Console.WriteLine("hello "+e.Message);
        }
    }

    public Employee? Delete(int eid){

        MySqlCommand cmd= new MySqlCommand("select * from employeesend");
        cmd.Connection=connection;
        Employee? employees=null;
        try
        {
            Console.WriteLine(eid);
            DataSet ds= new DataSet();
            MySqlDataAdapter sqlda= new MySqlDataAdapter(cmd);
            MySqlCommandBuilder cb=new MySqlCommandBuilder( sqlda);
            sqlda.Fill(ds);
            DataTable dt=ds.Tables[0];
            DataRowCollection rows= dt.Rows;
            DataRow[] d =dt.Select("EmployeeId="+eid);
            DataRow r=d[0];
            r.Delete();
            sqlda.Update(ds);
        }
        catch (Exception e)
        {
            Console.WriteLine("hello "+e.Message);
        }
        return employees;
    }

}
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//Controllers --> HomeController.cs
using System.Diagnostics;
using Microsoft.AspNetCore.Mvc;
using employees.Models;

namespace employees.Controllers;

public class HomeController : Controller
{
    private readonly ILogger<HomeController> _logger;

    public HomeController(ILogger<HomeController> logger)
    {
        _logger = logger;
    }

    public IActionResult Index()
    {
        return View();
    }

    public IActionResult Privacy()
    {
        return View();
    }

    [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
    public IActionResult Error()
    {
        return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
    }
}
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//Controller --> EmployeeController.cs
using System.Diagnostics;
using Microsoft.AspNetCore.Mvc;
using employees.Models;
using APP.Data;
using APP.Emp;
using System.Collections.Immutable;
namespace employees.Controllers;
public class EmployeeController : Controller
{
    MySqlData dt;
    public EmployeeController(){
        dt=new MySqlData();
    }
    public IActionResult Index()
    {
        List<Employee> emps=dt.GetAll();
        foreach (var item in emps)
        {
           Console.WriteLine(item.Name); 
        }
        return View(emps);
    }

        public IActionResult Details(int id)
    {
        Console.WriteLine(id);
        Employee e=dt.GetByID(id);
        return View(e);
        //return RedirectToAction("Employee","Index");
    }

       public IActionResult Edit(int id)
    {
        Console.WriteLine(id);
        Employee e=dt.GetByID(id);
        return View(e);
    }
    [HttpPost]
       public IActionResult Edit(Employee e)
    {
        //Console.WriteLine(e.Name);
        dt.Edit(e);
        return RedirectToAction("Index");
    }

       public IActionResult Add()
    {
        return View();
    }

    [HttpPost]
       public IActionResult Add(Employee e)
    {
        //Console.WriteLine(e.Name);
        dt.Add(e);
        return RedirectToAction("Index");
    }
    
     public IActionResult Delete(int id)
    {
        //Console.WriteLine(e.Name);
        dt.Delete(id);
        return RedirectToAction("Index");
    }
    
}
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//Views -- Employee --> Add.cshtml
@using APP.Emp;

@{
    <h1>Add new Employee</h1>
    <form method="post" asp-action="Add" asp-controller="Employee">
        <br/>
        Name : <input type="text" name="Name" value=""><br/>
        Salary : <input type="text" name="Salary" value=""><br/>
        DeptId : <input type="text" name="DepartmentId" value=""><br/>
        <input type="submit"  value="submit">
    </form>
}
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//Views -- Employee --> Details.cshtml
@using APP.Emp;

@{
    Employee emp=Model;
    <h1>Hello @emp.Name</h1>
    <hr/>
    <p>Id: @emp.EmployeeID</p>
    <p>Name: @emp.Name</p>
    <p>Salary : @emp.Salary</p>
    <p>DeparmentId: @emp.DepartmentId</p>
}
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//Views -- Employee --> Edit.cshtml
@using APP.Emp;

@{
    Employee emp=Model;
    <form method="post" asp-action="Edit" asp-controller="Employee">
        Id : <input type="text" name="EmployeeID" value="@emp.EmployeeID" readonly>
        <br/>
        Name : <input type="text" name="Name" value="@emp.Name"><br/>
        Salary : <input type="text" name="Salary" value="@emp.Salary"><br/>
        DeptId : <input type="text" name="DepartmentId" value="@emp.DepartmentId"><br/>
        <input type="submit"  value="submit">
    </form>
}
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//Views -- Employee --> Index.cshtml
@using APP.Emp;
<h1>All Employees</h1>
<Hr></Hr>
@{
    List<Employee> emps=Model;
    foreach(var emp in emps){
        <p>Name : @emp.Name <a href="/employee/details/@emp.EmployeeID">Details</a> <a href="/Employee/Edit/@emp.EmployeeID">Edit</a> <a href="/Employee/Delete/@emp.EmployeeID">Delete</a> </p>
    }

    <br/>
    <a href="/employee/add">Add new </a>
}
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//Program.cs
var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddControllersWithViews();

var app = builder.Build();

// Configure the HTTP request pipeline.
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Home/Error");
    // The default HSTS value is 30 days. You may want to change this for production scenarios, see https://aka.ms/aspnetcore-hsts.
    app.UseHsts();
}

app.UseHttpsRedirection();
app.UseStaticFiles();

app.UseRouting();

app.UseAuthorization();

app.MapControllerRoute(
    name: "default",
    pattern: "{controller=Home}/{action=Index}/{id?}");

app.Run();
