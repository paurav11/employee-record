<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" type="image/png" sizes="16x16" href="favicon/favicon-16x16.png">
    <link href="css/style.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css" rel="stylesheet">
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"></script>
    <title>Employee Record</title>
    <script type="text/javascript">
        getEmployees();
        getEmployeeCount();

        function getEmployees(){
            $.ajax({
                type: "GET",
                url: "http://localhost:8080/employees/all",
                context: document.body,
                success: function(result){
                    console.log(result);
                    createEmployeeTable(result);
                },
                error: function(error){
                    alert(error);
                }
            });
        }

        function getEmployeeCount(){
            $.ajax({
                type: "GET",
                url: "http://localhost:8080/employees/count",
                context: document.body,
                success: function(result){
                    document.getElementById("employee-count").innerHTML = result;
                },
                error: function(error){
                    alert(error);
                }
            });
        }

        function createEmployeeTable(result){
            var empList = JSON.parse(JSON.stringify(result));
            if (Object.keys(empList).length === 0){
                 let empData = "<p align='center'>Currently, no records are available. Click the <i class='fa-solid fa-plus' style='font-size: 1em;'></i> button to add an employee.</p>"
                 document.getElementById("employee-list-container").innerHTML = empData;
            } else {
                let empData = "<table class='table table-hover table-borderless' id='emp-table' width='100%' style='text-align:center;'>";
                empData += "<thead class='thead-light'><tr><th>#</th><th>First Name</th><th>Last Name</th><th>Email</th><th>Gender</th><th>DOB</th><th>Age</th><th>Salary</th><th>Status</th><th>Edit</th><th>Delete</th></tr></thead>";
                for (let x in empList) {
                    empData += "<tbody><tr style='vertical-align: middle;'><td>" + empList[x].employeeId + "</td><td>" + empList[x].firstName  +
                    "</td><td>"+ empList[x].lastName + "</td><td>" + empList[x].email + "</td><td>" + empList[x].gender + "</td><td>" +
                    empList[x].dob + "</td><td>" + empList[x].age + "</td><td>&#x20B9; " + empList[x].salary + "</td><td>" + empList[x].status +
                    "</td><td><button type='button' id='" + empList[x].employeeId + "' class='btn btn-sm edit' onclick='editEmployee(this.id)'><i class='fa-solid fa-pen-to-square'></i></button></td>" +
                    "<td><button type='button' id='" + empList[x].employeeId + "' class='btn btn-sm delete' onclick='deleteEmployee(this.id)'><i class='fa-solid fa-trash'></i></button></td></tr></tbody>";
                }
                empData += "</table>";
                document.getElementById("employee-list-container").innerHTML = empData;
            }
        }

        function validateEmployeeDetails(){
            var firstName = $('#first-name').val();
            var lastName = $('#last-name').val();
            var email = $('#email').val();
            var dob = $('#dob').val();
            var salary = $('#salary').val();
            if (firstName != "" && lastName != "" && email != "" && dob != "" && salary != ""){
                if (email.match("[a-zA-Z0-9._-]+@[a-z0-9.-]+\.[a-zA-Z]+")){
                    if (dob <= '2004-12-31') {
                       if (salary >= 1 && salary <= 10000000) {
                            addEmployee();
                       } else {
                            alert('Please enter salary between 1 to 10000000!');
                       }
                    } else {
                        alert('Please enter date that is less than 2004-12-31!');
                    }
                } else {
                    alert('Please enter email in proper format!');
                }
            } else {
                alert('Please enter all required field(s)!');
            }
        }

        function addEmployee(){
            var empData = {
                firstName: $('#first-name').val(),
                lastName: $('#last-name').val(),
                email: $('#email').val(),
                dob: $('#dob').val(),
                salary: $('#salary').val(),
                gender: $('#gender').val(),
                status: $('#status').val()
            };
            $.ajax({
                type: "POST",
                url: "http://localhost:8080/employees/add",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify(empData),
                context: document.body,
                success: function(result){
                    getEmployees();
                    getEmployeeCount();
                    resetForm();
                    alert('Employee added successfully.');
                    $("#add-employee-modal").modal('hide');
                    window.scrollTo(0, document.body.scrollHeight);
                },
                error: function(error){
                    alert('Employee already exists! Try entering again with different details.');
                    resetForm();
                }
            });
        }

        function deleteEmployee(empId){
           if (confirm("Are you sure you want to delete this employee?")) {
                $.ajax({
                    type: "DELETE",
                    url: "http://localhost:8080/employees/delete/" + empId,
                    context: document.body,
                    success: function(result){
                        getEmployees();
                        getEmployeeCount();
                        alert('Employee deleted successfully.');
                    },
                    error: function(error){
                        alert('Error occurred!');
                    }
                });
           } else {
                // Do nothing
           }
        }

        function resetForm(){
            document.getElementById("add-employee-form").reset();
        }
    </script>
</head>
<body>
    <h2 id="page-title" style="text-align: center; color: #ff5245;">List of Employees</h2>

    <!-- Total Employee Count and Add Employee Button -->
    <div style="display: flex; height: 70px; justify-content: space-between;">
        <p style="align-self: flex-start;">Total records: <span id="employee-count" style="color: #ff5245;"></span></p>
        <button type="button" id="add-employee-btn" class="btn btn-outline-dark btn-sm" data-bs-toggle="modal"
        data-bs-target="#add-employee-modal"><i class="fa-solid fa-plus" style="align-self: flex-end;"></i></button>
    </div>

    <!-- Add Employee Modal -->
    <div class="modal fade" id="add-employee-modal">
      <div class="modal-dialog">
        <div class="modal-content">

          <!-- Modal Header -->
          <div style="display: flex; justify-content: flex-end;">
            <button type="button" class="btn-close close" onclick="resetForm()" data-bs-dismiss="modal"></button>
          </div>
          <div class="modal-header">
            <h4 class="modal-title" style="color: #ff5245;">Add Employee</h4>
          </div>

          <!-- Modal body -->
          <div class="modal-body">
            <form id="add-employee-form">
              <div class="row">
                <div class="col">
                    <label for="first-name" class="form-label">First Name<span style="color: red;">&#42;</span></label>
                    <input type="text" class="form-control" id="first-name" size="50" placeholder="e.g. John"
                    name="firstName" autofocus required>
                </div>
                <div class="col">
                    <label for="last-name" class="form-label">Last Name<span style="color: red;">&#42;</span></label>
                    <input type="text" class="form-control" id="last-name" size="50" placeholder="e.g. Smith"
                    name="lastName"
                    required>
                </div>
              </div>
              <div class="mb-3">
                 <label for="email" class="form-label">Email<span style="color: red;">&#42;</span></label>
                 <input type="email" class="form-control" id="email" size="50" pattern="[a-zA-Z0-9._-]+@[a-z0-9.-]+\.[a-zA-Z]+" placeholder="abc@xyz.com" name="email" required>
              </div>
              <div class="row">
                  <div class="col">
                     <label for="dob" class="form-label">Date of Birth<span style="color: red;">&#42;</span></label>
                     <input type="date" step="1" max="2004-12-31" class="form-control" id="dob" name="dob" required>
                  </div>
                  <div class="col">
                     <label for="salary" class="form-label">Salary (INR)<span style="color: red;">&#42;</span></label>
                     <input type="number" step="0.01" min="1" max="10000000" class="form-control" id="salary"
                     placeholder="e.g. 25000.00" name="salary" required>
                  </div>
              </div>
              <div class="row">
                <div class="col">
                    <label for="gender" class="form-label">Gender<span style="color: red;">&#42;</span></label>
                    <select id="gender" class="form-select" name="gender" required>
                        <option>Male</option>
                        <option>Female</option>
                        <option>Other</option>
                    </select>
                </div>
                <div class="col">
                    <label for="status" class="form-label">Status<span style="color: red;">&#42;</span></label>
                    <select id="status" class="form-select" name="status" required>
                        <option>Active</option>
                        <option>Inactive</option>
                    </select>
                </div>
              </div>
              <p style="color: red; font-size: 0.8em;">(*) marked fields are mandatory.</p>

              <!-- Modal Footer -->
              <div class="modal-footer">
                <button type="button" id="cancel-btn" class="btn btn-outline-danger" onclick="resetForm()" data-bs-dismiss="modal" style="margin-right:10px;">Cancel</button>
                <button type="button" id="submit-btn" onclick="validateEmployeeDetails()" class="btn btn-outline-dark">Save</button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>

    <!-- Employee List Container -->
    <div class="table-responsive" id="employee-list-container"></div>
</body>
</html>