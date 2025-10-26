using HydroQuestApi.Models;
using Microsoft.EntityFrameworkCore;

namespace HydroQuestApi.Data
{
    public class AppDbContext : DbContext
    {
        public DbSet<User> Users { get; set; }
        public DbSet<HydrationLog> HydrationLogs { get; set; }
        public DbSet<Gamification> Gamifications { get; set; }

        public AppDbContext(DbContextOptions<AppDbContext> options) : base(options) { }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<User>().HasKey(u => u.GoogleId);
            modelBuilder.Entity<Gamification>().HasKey(g => g.UserId);
        }
    }
}
