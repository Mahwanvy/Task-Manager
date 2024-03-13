
document.addEventListener('DOMContentLoaded', function() {
    const logoutBtn = document.getElementById('logout');

    logoutBtn.addEventListener('click', function() {
		
        fetch('/logout', {
            method: 'POST', 
            credentials: 'same-origin' 
        })
        .then(response => {
            if (response.ok) {
                window.location.href = '/login'; 
            } else {
                console.error('Failed to logout');
            }
        })
        .catch(error => {
            console.error('Error during logout:', error);
        });
    });
});

function show(){
	document.getElementById('sidebar').classList.toggle('active');
}
 function displaySuccessMessage(message) {
    const successMessage = document.getElementById('success-message');
    const successMessageDiv = document.getElementById('success-message-div');
    successMessage.textContent = message;
    successMessageDiv.style.display = 'block';
    setTimeout(function() {
        successMessageDiv.style.display = 'none';
    }, 3000);
}

document.addEventListener('DOMContentLoaded', function() {
 const buttons = {
        'myTasksBtn': filterMyTasks,
        'sharedTasksBtn': filterSharedTasks,
        'invitationsBtn': filterInvitedTasks,
        'allTasksBtn': fetchAndPopulateTasks
    };

    Object.keys(buttons).forEach(buttonId => {
        const button = document.getElementById(buttonId);
        button.addEventListener('click', function() {
            toggleActiveButton(buttonId);
            buttons[buttonId]();
        });
    });

    const taskTableBody = document.getElementById('taskTableBody');

    function toggleActiveButton(activeButtonId) {
        Object.keys(buttons).forEach(buttonId => {
            const button = document.getElementById(buttonId);
            if (buttonId === activeButtonId) {
                button.classList.add('active');
            } else {
                button.classList.remove('active');
            }
        });
    }
    function filterMyTasks() {
        filterTasks(row => {
            const ownerCell = row.querySelector('td:nth-child(8)').textContent.trim().toLowerCase();
            return ownerCell === 'yes';
        });
    }

    function filterSharedTasks() {
        filterTasks(row => {
            const ownerCell = row.querySelector('td:nth-child(8)').textContent.trim().toLowerCase();
            const collaboratorCell = row.querySelector('td:nth-child(7)').textContent.trim().toLowerCase();
            return ownerCell === 'yes' && collaboratorCell !== '';
        });
    }

    function filterInvitedTasks() {
        filterTasks(row => {
            const ownerCell = row.querySelector('td:nth-child(8)').textContent.trim().toLowerCase();
            return ownerCell === 'no';
        });
    }

function filterTasks(filterCondition) {
    const rows = taskTableBody.querySelectorAll('tr');
    let filteredRowCount = 0;

    rows.forEach(row => {
        if (filterCondition(row)) {
            row.style.display = '';
            filteredRowCount++;
        } else {
            row.style.display = 'none';
        }
    });

    
    if (filteredRowCount === 0) {
        noTasksMessage.style.display = 'block';
    } else {
        noTasksMessage.style.display = 'none';
    }    
 }
    });
    


document.addEventListener("DOMContentLoaded", function () {
    const filterIcon = document.getElementById("filter-status-icon");
    const statusFilterOptions = document.getElementById("status-filter-options");

    filterIcon.addEventListener("click", function () {
        if (statusFilterOptions.style.display === "none" || statusFilterOptions.style.display === "") {
            statusFilterOptions.style.display = "block";
        } else {
            statusFilterOptions.style.display = "none";
        }
    });

    const statusFilterList = document.getElementById("status-filter-list");

    statusFilterList.addEventListener("click", function (event) {
        const selectedStatus = event.target.dataset.status;
      
        filterTableByStatus(selectedStatus);

        statusFilterOptions.style.display = "none";
    });
});

function filterTableByStatus(status) {
    const tableRows = document.querySelectorAll("#taskTableBody tr");
	let filteredRowCount = 0;
    tableRows.forEach(function (row) {
        const statusCell = row.querySelector("td:nth-child(5)"); 

        if (status === "all" || (statusCell && statusCell.textContent.trim().toLowerCase() === status)) {
            row.style.display = ""; 
            filteredRowCount++;
        } else {
            row.style.display = "none"; 
        }
    });
     if (filteredRowCount === 0) {
        noTasksMessage.style.display = 'block';
    } else {
        noTasksMessage.style.display = 'none';
    }    
}


function deleteTask(icon) {
    
    const taskId = icon.closest('tr').querySelector('td:nth-child(1)').textContent;
	var userId = document.getElementById('user-id').value;
    
    if (confirm('Are you sure you want to delete this task?')) {
        
        fetch(`/tasks/${userId}/${taskId}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (response.ok) {
                fetchAndPopulateTasks();
                displaySuccessMessage('Task deleted successfully.');
            } else {
                throw new Error('Failed to delete task');
            }
        })
        .catch(error => {
            console.error('Error deleting task:', error);
        });
    }
}
function formatDateForInput(dateString) {
    const dateParts = dateString.split('-'); 
    const formattedDate = `${dateParts[2]}-${dateParts[1]}-${dateParts[0]}`; 
    return formattedDate;
}
 function fetchAndPopulateTasks() {
	 var userId = document.getElementById('user-id').value;
            fetch(`/tasks/${userId}`)
            .then(response => response.json())
            .then(data => {
                const tableBody = document.getElementById('taskTableBody');
                var noTasksMessage = document.getElementById("noTasksMessage");
                tableBody.innerHTML = ''; 

                data.forEach(task => {
                    const row = document.createElement('tr');
                    row.innerHTML = `
                    	<td style="display:none;">${task.id}</td>
                        <td class="element-with-scroll">${task.title}</td>
			            <td class="element-with-scroll" style="padding-left: 20px;">${task.description}</td>
			            <td style="text-align: center;">${formatDate(task.dueDate)}</td>
			            <td style="text-align: center;">${task.status}</td>
			            <td style="text-align: center;"></td>
			        	<td style="display:none;"></td>
			        	<td style="display:none;">${task.owner.id == userId ?"Yes" : "No"}</td>
                    `;
                    
                const collaboratorsEmails = task.collaborators.map(collaborator => collaborator.email).join(', ');
                const ContributorTd = row.querySelector('td:nth-child(7)');
                ContributorTd.textContent = collaboratorsEmails;
                const ActionTd = row.querySelector('td:nth-child(6)');
                if(task.owner.id == userId){
					 ActionTd.innerHTML =`<td	>
				                                <span class="material-symbols-outlined pointer-btn" title="Edit" onclick="openEditTaskModal(this)">edit</span>
				                                <span class="material-symbols-outlined pointer-btn" title="Delete" onclick="deleteTask(this)">delete</span>
				                            </td>`;
				}else{
					 ActionTd.innerHTML =`<td>
				                                <span class="material-symbols-outlined pointer-btn" title="Edit" onclick="openEditTaskModal(this)">edit</span>
				                            </td>`;
				}
                
                tableBody.appendChild(row);
                });
                if( tableBody.innerHTML ==''){ noTasksMessage.style.display = "block";
	            } else {
	                noTasksMessage.style.display = "none";
	            }
            })
            .catch(error => {
                console.error('Error fetching tasks:', error);
            });
        }

        window.onload = function() {
            fetchAndPopulateTasks();
        }
 
 function getTodayDate() {
    const today = new Date();
    const year = today.getFullYear();
    let month = today.getMonth() + 1;
    let day = today.getDate();

    if (month < 10) {
        month = '0' + month;
    }
    if (day < 10) {
        day = '0' + day;
    }

    return `${year}-${month}-${day}`;
}

 function formatDate(dateString) {
    const date = new Date(dateString);
    const day = date.getDate();
    const month = date.getMonth() + 1;
    const year = date.getFullYear();

    
    const formattedDay = day < 10 ? '0' + day : day;
    const formattedMonth = month < 10 ? '0' + month : month;

    return formattedDay + '-' + formattedMonth + '-' + year;
}       
 function openEditTaskModal(icon) {
    const taskRow = icon.closest('tr');
    const taskId = taskRow.querySelector('td:nth-child(1)').textContent;
    const taskTitle = taskRow.querySelector('td:nth-child(2)').textContent;
    const taskDescription = taskRow.querySelector('td:nth-child(3)').textContent;
    const taskDueDate = taskRow.querySelector('td:nth-child(4)').textContent;
    const taskStatus = taskRow.querySelector('td:nth-child(5)').textContent;
    const isOwner = taskRow.querySelector('td:nth-child(8)').textContent;

    
    document.getElementById('task-id').value = taskId;
    document.getElementById('task-title').value = taskTitle;
    document.getElementById('task-description').value = taskDescription;
    document.getElementById('task-due-date').value = formatDateForInput(taskDueDate);
    document.getElementById('contributor-email').value="";
   
	const statusDropdown = document.getElementById('task-status');
    for (let i = 0; i < statusDropdown.options.length; i++) {
        if (statusDropdown.options[i].value === taskStatus) {
            statusDropdown.selectedIndex = i;
            break;
        }
    }
    
    document.getElementById('task-modal').style.display = 'block';
     document.querySelector('.create-btn').style.display = 'none';
    document.querySelector('.save-btn').style.display = 'block';
    if (isOwner.toLowerCase() === 'no') {
    document.querySelector('.collaborator-container').style.display = 'none';
	}else{
	document.querySelector('.collaborator-container').style.display = 'block';	
	}
    
    const contributorList = document.querySelector('.contibutor-list');

    contributorList.innerHTML = '';
    const collaboratorsCell = taskRow.querySelector('td:nth-child(7)');
	const collaboratorsText = collaboratorsCell.textContent.trim(); 

if (collaboratorsText) {
    const collaborators = collaboratorsText.split(', ');

    collaborators.forEach(email => {
		
		const existingEmails = Array.from(contributorList.querySelectorAll('.contibutor-list-item-label span')).map(span => span.textContent.trim());
	    if (existingEmails.includes(email)) {
	        alert("User is already added to the task.");
	        document.getElementById('contributor-email').value="";
	        return;
	    }
        const listItem = document.createElement('li');
        listItem.classList.add('contibutor-list-item');
        
        const label = document.createElement('label');
        label.classList.add('contibutor-list-item-label');
        const emailSpan = document.createElement('span');
        emailSpan.textContent = email;
        label.appendChild(emailSpan);

        const removeBtn = document.createElement('span');
        removeBtn.classList.add('remove-btn');
        removeBtn.setAttribute('title', 'Remove Contributor');
        removeBtn.addEventListener('click', function() {
            listItem.remove();
        });

        
        listItem.appendChild(label);
        listItem.appendChild(removeBtn);

        
        contributorList.appendChild(listItem);
    });
    }
    
}


document.addEventListener('DOMContentLoaded', function() {
	const taskDueDateInput = document.getElementById('task-due-date');
    taskDueDateInput.setAttribute('min', getTodayDate());
    
    const addTaskBtn = document.getElementById('add-task-btn');
    const taskModal = document.getElementById('task-modal');
    const createTaskBtn = document.getElementById('create-task-btn');
    const saveTaskBtn = document.getElementById('save-task-btn');
    
     createTaskBtn.addEventListener('click', function(event) {
        
        event.preventDefault();
        
        
        saveTask('create');
    });

    saveTaskBtn.addEventListener('click', function(event) {
        event.preventDefault();
        
        
        saveTask('save');
    });


    addTaskBtn.addEventListener('click', openTaskModal);

    function openTaskModal() {
		document.querySelector('.create-btn').style.display = 'block';
    	document.querySelector('.save-btn').style.display = 'none';
        taskModal.style.display = 'block';
        contributorList.innerHTML = '';
        document.getElementById('contributor-email').value="";
        const statusDropdown = document.getElementById('task-status');
    	statusDropdown.selectedIndex = 0;
    }

   function saveTask(action) {
    const id = document.getElementById('task-id').value.trim();
    const title = document.getElementById('task-title').value.trim();
    const description = document.getElementById('task-description').value.trim();
    const dueDate = document.getElementById('task-due-date').value;
    const statusDropdown = document.getElementById('task-status');
    const status = statusDropdown.options[statusDropdown.selectedIndex].value;
    const contributorListItems = document.querySelectorAll('.contibutor-list-item');
    const contributors = [];
    contributorListItems.forEach(listItem => {
        const emailSpan = listItem.querySelector('span');
        if (emailSpan) {
            contributors.push(emailSpan.textContent.trim());
        }
    });

    if (!title || !description || !dueDate) {
        alert('Please fill in all fields');
        return;
    }

    fetch('/user/lookup', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(contributors)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Failed to retrieve collaborator IDs');
        }
        return response.json();
    })
    .then(data => {
        const collaborators = data;

        const task = {
            id,
            title,
            description,
            dueDate,
            status,
            collaborators:  collaborators.map(id => ({ "id": id }))
        };

        if (action === 'save') {
            updateTask(task);    
    		var successMessageVal="Task Updated Successfully!" 
        } else if (action === 'create') {
            createTask(task);
            var successMessageVal="Task Added Successfully!" 
        }

        closeTaskModal();
        displaySuccessMessage(successMessageVal);
    })
    .catch(error => {
        console.error('Error retrieving collaborator IDs:', error);
    });
}

    function closeTaskModal() {
        taskModal.style.display = 'none';
        clearTaskForm();
    }
    function clearTaskForm() {
		document.getElementById('task-id').value = '';
        document.getElementById('task-title').value = '';
        document.getElementById('task-description').value = '';
        document.getElementById('task-due-date').value = '';
    }
 	function updateTask(task) {
		 debugger
    const taskId =document.getElementById('task-id').value.trim();
    var userId = document.getElementById('user-id').value;
    const updateUrl = `/tasks/${userId}/${taskId}`; 
    
    fetch(updateUrl, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(task)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Failed to update task');
        }
        fetchAndPopulateTasks();
        return response.json();
    })
    .catch(error => {
        console.error('Error updating task:', error);
    });
}

function createTask(task) {
	var userId = document.getElementById('user-id').value;
    fetch(`/tasks?userId=${userId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(task),
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Failed to create task');
        }
        fetchAndPopulateTasks();
        return response.json();
         
    })
    .catch(error => {
        console.error('Error creating task:', error);
    });
}
 	

    const closeButton = document.querySelector('.close');

    closeButton.addEventListener('click', closeTaskModal);
    
    const addContributorBtn = document.getElementById('add-contributor');
    const contributorInput = document.querySelector('.contributor-input');
    const contributorList = document.querySelector('.contibutor-list');

   addContributorBtn.addEventListener('click', function() {
    const email = contributorInput.value.trim();
    const currentUserEmail = document.getElementById('user-email').innerHTML;
    if (email === currentUserEmail) {
        alert('You cannot add yourself as a collaborator.');
        contributorInput.value ="";
        return;
    }

    
    if (!isValidEmail(email)) {
        alert('Please enter a valid email address.');
        return;
    }

    fetch(`/user/check?email=${email}`)
    .then(response => response.text())
		    .then(data => {
		        if (Boolean(data)) {
        	    addCollaborator(email);
           } else {
		        	document.querySelector('.contributor-input').value="";
		        	alert('User with the provided email does not exist.');
		        }
		    })
        .catch(error => {
            console.error('Error checking user existence:', error);
            alert('Error checking user existence. Please try again.');
        });
});

function addCollaborator(email) {
    const existingEmails = Array.from(contributorList.querySelectorAll('.contibutor-list-item-label span')).map(span => span.textContent.trim());
	    if (existingEmails.includes(email)) {
	        alert("User is already added to the task.");
	        document.getElementById('contributor-email').value="";
	        return;
	    }
    const listItem = document.createElement('li');
    listItem.classList.add('contibutor-list-item');

    const label = document.createElement('label');
    label.classList.add('contibutor-list-item-label');
    const emailSpan = document.createElement('span');
    emailSpan.textContent = email;
    label.appendChild(emailSpan);

    const removeBtn = document.createElement('span');
    removeBtn.classList.add('remove-btn');
    removeBtn.setAttribute('title', 'Remove Contributor');
    removeBtn.addEventListener('click', function() {
   
        listItem.remove();
    });


    listItem.appendChild(label);
    listItem.appendChild(removeBtn);

    contributorList.appendChild(listItem);

    contributorInput.value = '';
}

function isValidEmail(email) {

    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailPattern.test(email);
}
});