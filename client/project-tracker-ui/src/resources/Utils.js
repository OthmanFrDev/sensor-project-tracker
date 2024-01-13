const formatDate = (date) => typeof date !== 'string' ? date.toISOString().split('T')[0] : date

export {
    formatDate
}