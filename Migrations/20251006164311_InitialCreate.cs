using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace HydroQuestApi.Migrations
{
    /// <inheritdoc />
    public partial class InitialCreate : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "Gamifications",
                columns: table => new
                {
                    UserId = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    Streak = table.Column<int>(type: "int", nullable: false),
                    CompanionLevel = table.Column<int>(type: "int", nullable: false),
                    Achievements = table.Column<string>(type: "nvarchar(max)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Gamifications", x => x.UserId);
                });

            migrationBuilder.CreateTable(
                name: "HydrationLogs",
                columns: table => new
                {
                    Id = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    UserId = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Amount = table.Column<int>(type: "int", nullable: false),
                    Timestamp = table.Column<DateTime>(type: "datetime2", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_HydrationLogs", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "Users",
                columns: table => new
                {
                    GoogleId = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    DailyGoal = table.Column<int>(type: "int", nullable: false),
                    Units = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Theme = table.Column<string>(type: "nvarchar(max)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Users", x => x.GoogleId);
                });
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "Gamifications");

            migrationBuilder.DropTable(
                name: "HydrationLogs");

            migrationBuilder.DropTable(
                name: "Users");
        }
    }
}
