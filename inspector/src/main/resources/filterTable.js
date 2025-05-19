/**
 * Filters table rows based on the text input across all columns.
 */
function filterTable() {
    const table = document.querySelector('table');
    const rows = Array.from(table.rows).slice(1); // Exclude header row
    const filterInputs = document.querySelectorAll('.filter-input');

    rows.forEach(row => {
        let isVisible = true;

        // Check each column's filter input
        filterInputs.forEach((filterInput, columnIndex) => {
            const filterText = filterInput.value.trim().toLowerCase();
            const cellText = row.cells[columnIndex].textContent.trim().toLowerCase();

            if (filterText && !cellText.includes(filterText)) {
                isVisible = false;
            }

            // Show or hide the clear button
            const clearButton = filterInput.nextElementSibling;
            if (filterText) {
                clearButton.style.display = 'inline';
            } else {
                clearButton.style.display = 'none';
            }
        });

        // Show or hide the row based on the filter
        row.style.display = isVisible ? '' : 'none';
    });
}

/**
 * Clears the filter input for a specific column.
 * @param {HTMLInputElement} input - The filter input element.
 */
function clearFilter(input) {
    input.value = '';
    filterTable(); // Reapply the filter after clearing
}
