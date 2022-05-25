<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="css/style.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css" rel="stylesheet">
    <title>Employee Record</title>
</head>
<body>
    <h2 id="page-title" style="text-align: center; color: #f06e65;"><b>List of Employees</b></h2>

    <!-- Add Employee Button -->
    <div style="display: flex; justify-content: flex-end;" data-bs-toggle="tooltip" title="Add Employee">
        <button type="button" id="add-employee-btn" class="btn btn-outline-primary" data-bs-toggle="modal"
        data-bs-target="#add-employee-modal"><i class="fa-solid fa-plus" style="font-size: 1em;"></i></button>
    </div>

    <!-- Add Employee Modal -->
    <div class="modal fade" id="add-employee-modal">
      <div class="modal-dialog">
        <div class="modal-content">

          <!-- Modal Header -->
          <div style="display: flex; justify-content: flex-end;">
            <button type="button" class="btn-close" onclick="resetForm()" data-bs-dismiss="modal"></button>
          </div>
          <div class="modal-header">
            <h4 class="modal-title" style="color: #f06e65;"><b>Add Employee</b></h4>
          </div>

          <!-- Modal body -->
          <div class="modal-body">
            <form id="add-employee-form" action="/employees/add" method="POST">
              <div class="row">
                <div class="col">
                    <label for="first-name" class="form-label"><b>First Name<span style="color: red;">*</span></b></label>
                    <input type="text" class="form-control" id="first-name" size="50" placeholder="e.g. John"
                    name="firstName" autofocus required>
                </div>
                <div class="col">
                    <label for="last-name" class="form-label"><b>Last Name<span style="color: red;">*</span></b></label>
                    <input type="text" class="form-control" id="last-name" size="50" placeholder="e.g. Smith"
                    name="lastName"
                    required>
                </div>
              </div>
              <div class="mb-3">
                 <label for="email" class="form-label"><b>Email<span style="color: red;">*</span></b></label>
                 <input type="email" class="form-control" id="email" size="50" pattern="[a-zA-Z0-9._-]+@[a-z0-9.-]+\.[a-zA-Z]+" placeholder="abc@xyz.com" name="email" required>
              </div>
              <div class="row">
                  <div class="col">
                     <label for="dob" class="form-label"><b>Date of Birth<span style="color: red;">*</span></b></label>
                     <input type="date" step="1" max="2004-12-31" class="form-control" id="dob" name="dob" required>
                  </div>
                  <div class="col">
                     <label for="salary" class="form-label"><b>Salary (INR)<span style="color: red;">*</span></b></label>
                     <input type="number" step="0.01" min="0" max="10000000" class="form-control" id="salary"
                     placeholder="e.g. 25000.00" name="salary" required>
                  </div>
              </div>
              <div class="row">
                <div class="col">
                    <label for="gender" class="form-label"><b>Gender<span style="color: red;">*</span></b></label>
                    <select id="gender" class="form-select" name="gender" required>
                        <option>Male</option>
                        <option>Female</option>
                        <option>Other</option>
                    </select>
                </div>
                <div class="col">
                    <label for="status" class="form-label"><b>Status<span style="color: red;">*</span></b></label>
                    <select id="status" class="form-select" name="status" required>
                        <option>Active</option>
                        <option>Inactive</option>
                    </select>
                </div>
              </div>
              <p style="color: red; font-size: 0.8em;">(*) marked fields are mandatory.</p>

              <!-- Modal Footer -->
              <div class="modal-footer">
                <button type="button" id="cancel-btn" class="btn btn-outline-danger" onclick="resetForm()"
                data-bs-dismiss="modal" style="margin-right:10px;">Cancel</button>
                <button type="submit" id="submit-btn" class="btn btn-outline-primary">Submit</button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>

    <!-- Employee List Container -->
    <div id="employee-list-container">
        <p>Inside the container...</p>
    </div>
    <script>
        function resetForm(){
            document.getElementById("add-employee-form").reset();
        }
    </script>
</body>
</html>