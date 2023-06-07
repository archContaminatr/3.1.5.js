const urlUsers = "http://localhost:8080/api/users"
const urlRoles = "http://localhost:8080/api/roles"

readAllUsers()
readAllRoles()

let allUsers = []
function readAllUsers() {
    fetch(urlUsers)
        .then(response => response.json())
        .then(users => {
            users.forEach(user => {
                allUsers.push(user)
                fillingUserToUsersTable(user)
            })
        })
}

function fillingUserToUsersTable(user) {
    $("#users-table-body").append(
        "<tr>" +
        "<td>" + user.id + "</td>" +
        "<td>" + user.firstName + "</td>" +
        "<td>" + user.lastName + "</td>" +
        "<td>" + user.email + "</td>" +
        "<td>" + user.roles.map(role => role.role.replaceAll("ROLE_", "")) + "</td>" +
        "<td>" + "<button class='btn btn-warning btn-sm' onclick='readUserByIdForUpdate(" + user.id + ")' data-bs-target='#update-user' data-bs-toggle='modal'>Edit</button>" + "</td>" +
        "<td>" + "<button class='btn btn-danger btn-sm' onclick='readUserByIdForDelete(" + user.id + ")' data-bs-target='#delete-user' data-bs-toggle='modal'>Delete</button>" + "</td>" +
        "</tr>")
}

let allRoles = []
function readAllRoles() {
    fetch(urlRoles)
        .then(response => response.json())
        .then(roles => {
            roles.forEach(role => {
                allRoles.push(role)
            })
        })
}

function getRoles(list) {
    let roles = []
    for (let i = 0; i < list.length; i++) {
        if (list[i] === "1") {
            roles.push(allRoles[0])
        }
        if (list[i] === "2") {
            roles.push(allRoles[1])
        }
    }
    return roles
}

function createUser() {
    let user = {
        firstName: $("#firstname-new").val(),
        lastName: $("#lastname-new").val(),
        email: $("#email-new").val(),
        password: $("#password-new").val(),
        roles: getRoles($("#roles-new").val())
    }

    fetch(urlUsers, {
        method: "POST",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json",
            "Referer": null
        },
        body: JSON.stringify(
            {
                firstName: user.firstName,
                lastName: user.lastName,
                email: user.email,
                password: user.password,
                roles: user.roles
            }
        )
    })
        .then(response => response.json())
        .then(user => fillingUserToUsersTable(user))
        .then(() => $("#create-user-form").trigger("reset"))
        .catch(error => console.log(error))
}

function readUserByIdForUpdate(id) {
    fetch(urlUsers + "/" + id)
        .then(response => response.json())
        .then(user => {
            $("#id-update").val(user.id)
            $("#firstname-update").val(user.firstName)
            $("#lastname-update").val(user.lastName)
            $("#email-update").val(user.email)
            $("#password-update").val(user.password)
        })
}

function readUserByIdForDelete(id) {
    fetch(urlUsers + "/" + id)
        .then(response => response.json())
        .then(user => {
            $("#id-delete").val(user.id)
            $("#firstname-delete").val(user.firstName)
            $("#lastname-delete").val(user.lastName)
            $("#email-delete").val(user.email)
            $("#password-delete").val(user.password)
        })
}

function updateUser() {
    let user = {
        id: $("#id-update").val(),
        firstName: $("#firstname-update").val(),
        lastName: $("#lastname-update").val(),
        email: $("#email-update").val(),
        password: $("#password-update").val(),
        roles: getRoles($("#roles-update").val())
    }

    fetch(urlUsers, {
        method: "PUT",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json",
            "Referer": null
        },
        body: JSON.stringify(
            {
                id: user.id,
                firstName: user.firstName,
                lastName: user.lastName,
                email: user.email,
                password: user.password,
                roles: user.roles
            }
        )
    }).then(() => {
        $("#users-table-body").empty()
        readAllUsers()
    })

}

function deleteUser() {
    fetch(urlUsers + "/" + $("#id-delete").val(), {method: "DELETE"})
        .then(() => {
            $("#users-table-body").empty()
            readAllUsers()
        })
}