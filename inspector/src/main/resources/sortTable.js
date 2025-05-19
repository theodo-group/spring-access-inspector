/**
 * Sorts a table column based on the given column index.
 * @param {number} columnIndex - The index of the column to sort.
 */
function sortTable(columnIndex) {
    const table = document.querySelector('table');
    const headers = table.querySelectorAll('th');
    const rows = Array.from(table.rows).slice(1); // Exclude header row
    let direction = 'asc'; // Default sort direction

    // Determine the current sort direction
    const currentIcon = headers[columnIndex].querySelector('.sort-icon');
    if (currentIcon.textContent === '▼') {
        direction = 'desc';
    }

    // Reset sort icons for all headers
    resetSortIcons(headers);

    // Sort rows based on the selected column and direction
    const sortedRows = rows.sort((rowA, rowB) => {
        const cellA = rowA.cells[columnIndex].textContent.trim().toLowerCase();
        const cellB = rowB.cells[columnIndex].textContent.trim().toLowerCase();

        if (direction === 'asc') {
            return cellA > cellB ? 1 : cellA < cellB ? -1 : 0;
        } else {
            return cellA < cellB ? 1 : cellA > cellB ? -1 : 0;
        }
    });

    // Update the table with sorted rows
    const tableBody = table.querySelector('tbody');
    sortedRows.forEach(row => tableBody.appendChild(row));

    // Update the sort icon for the selected column
    currentIcon.textContent = direction === 'asc' ? '▼' : '▲';
}

/**
 * Resets the sort icons for all table headers.
 * @param {NodeList} headers - The table header elements.
 */
function resetSortIcons(headers) {
    headers.forEach(header => {
        const icon = header.querySelector('.sort-icon');
        if (icon) {
            icon.textContent = '';
        }
    });
}
