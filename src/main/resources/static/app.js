const API_BASE = '/api/todos';

async function checkHealth() {
    const statusEl = document.getElementById('apiStatus');
    try {
        const response = await fetch(`${API_BASE}/health`);
        if (response.ok) {
            const text = await response.text();
            statusEl.textContent = 'Online';
            statusEl.className = 'status-ok';
        } else {
            throw new Error('API error');
        }
    } catch (error) {
        statusEl.textContent = 'Offline';
        statusEl.className = 'status-error';
    }
}

async function loadTodos() {
    const listEl = document.getElementById('todoList');
    try {
        const response = await fetch(API_BASE);
        if (!response.ok) throw new Error('Failed to load');
        const todos = await response.json();
        renderTodos(todos);
    } catch (error) {
        listEl.innerHTML = '<p class="empty">Failed to load tasks. Please try again.</p>';
    }
}

function renderTodos(todos) {
    const listEl = document.getElementById('todoList');
    if (!todos || todos.length === 0) {
        listEl.innerHTML = '<p class="empty">No tasks yet. Add one above!</p>';
        return;
    }
    
    listEl.innerHTML = todos.map(todo => `
        <div class="todo-item ${todo.completed ? 'completed' : ''}">
            <div class="todo-content">
                <div class="todo-title">${escapeHtml(todo.title)}</div>
                ${todo.description ? `<div class="todo-description">${escapeHtml(todo.description)}</div>` : ''}
            </div>
            <div class="todo-actions">
                ${!todo.completed ? `<button class="btn-complete" onclick="completeTodo(${todo.id})">Complete</button>` : ''}
                <button class="btn-delete" onclick="deleteTodo(${todo.id})">Delete</button>
            </div>
        </div>
    `).join('');
}

function escapeHtml(text) {
    if (!text) return '';
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

async function addTodo() {
    const titleInput = document.getElementById('todoTitle');
    const descInput = document.getElementById('todoDescription');
    const title = titleInput.value.trim();
    
    if (!title) {
        alert('Please enter a task title');
        return;
    }
    
    const todo = {
        title: title,
        description: descInput.value.trim(),
        completed: false
    };
    
    try {
        const response = await fetch(API_BASE, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(todo)
        });
        
        if (response.ok) {
            titleInput.value = '';
            descInput.value = '';
            loadTodos();
        } else {
            alert('Failed to add task');
        }
    } catch (error) {
        alert('Error adding task');
    }
}

async function completeTodo(id) {
    try {
        const response = await fetch(`${API_BASE}/${id}/complete`, { method: 'PATCH' });
        if (response.ok) {
            loadTodos();
        }
    } catch (error) {
        alert('Error completing task');
    }
}

async function deleteTodo(id) {
    if (!confirm('Are you sure you want to delete this task?')) return;
    
    try {
        const response = await fetch(`${API_BASE}/${id}`, { method: 'DELETE' });
        if (response.ok || response.status === 204) {
            loadTodos();
        }
    } catch (error) {
        alert('Error deleting task');
    }
}

// Initialize
document.addEventListener('DOMContentLoaded', () => {
    checkHealth();
    loadTodos();
});
